package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
@Repository
public class InMemoryWordsRepository extends AssertionConcern implements WordsRepository {
    private static final AtomicLong nextId = new AtomicLong();

    private final Map<Long, Word> wordsById = new HashMap<>();
    // helper for quick search over words value
    private final Map<String, Long> idsByChars = new HashMap<>();
    // helper for quick getting random words id for category. This is bi-directional map splitted into 2 maps.
    private final List<Map<Long, Long>> idxToIdMapsForCategory;
    private final List<Map<Long, Long>> idToIdxMapsForCategory;

    private final ReadWriteLock sync = new ReentrantReadWriteLock();
    private final Random random = new Random(System.currentTimeMillis());

    {
        final int categoriesCount = WordCategory.values().length;
        idxToIdMapsForCategory = new ArrayList<>(categoriesCount);
        for (int i = 0; i < categoriesCount; ++i) {
            idxToIdMapsForCategory.add(new HashMap<>());
        }

        idToIdxMapsForCategory = new ArrayList<>(categoriesCount);
        for (int i = 0; i < categoriesCount; ++i) {
            idToIdxMapsForCategory.add(new HashMap<>());
        }
    }

    @Override
    public <S extends Word> S save(S word) {
        assertArgumentNotNull(word, "words must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();

            final String chars = word.getChars().toLowerCase();
            Long idInDb = idsByChars.get(chars);
            if (idInDb == null) {
                idInDb = nextId.getAndIncrement();
            }

            word.setId(idInDb);
            idsByChars.put(chars, idInDb);
            wordsById.put(idInDb, word);

            for (WordCategory wordCategory : word.getCategories()) {
                final Map<Long, Long> idxToIdMap = getIdxToIdMapForCategory(wordCategory);
                final Map<Long, Long> idToIdxMap = getIdToIdxMapForCategory(wordCategory);

                Long nextIdx = (long)idxToIdMap.size();
                idxToIdMap.put(nextIdx, idInDb);
                idToIdxMap.put(idInDb, nextIdx);
            }
        } finally {
            writeLock.unlock();
        }

        return word;
    }

    @Override
    public <S extends Word> List<S> save(Iterable<S> words) {
        assertArgumentNotNull(words, "words must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            final List<S> result = new ArrayList<>();

            for (S word : words) {
                result.add(save(word));
            }

            return result;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public Word findOne(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return wordsById.get(id);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Word findWord(String chars) {
        assertArgumentNotEmpty(chars, "chars must not be null or empty.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            final Long id = idsByChars.get(chars.toLowerCase());
            if (id != null) {
                return wordsById.get(id);
            }

            return null;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Word findRandomWordForCategory(WordCategory wordCategory) {
        assertArgumentNotNull(wordCategory, "wordCategory must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            final Map<Long, Long> idxToIdMap = getIdxToIdMapForCategory(wordCategory);

            if (!idxToIdMap.isEmpty()) {
                final Long index = (long) random.nextInt(idxToIdMap.size());
                return wordsById.get(idxToIdMap.get(index));
            }

            return null;
        } finally {
            readLock.unlock();
        }
    }

    private Map<Long, Long> getIdxToIdMapForCategory(WordCategory wordCategory) {
        return idxToIdMapsForCategory.get(wordCategory.ordinal());
    }

    private Map<Long, Long> getIdToIdxMapForCategory(WordCategory wordCategory) {
        return idToIdxMapsForCategory.get(wordCategory.ordinal());
    }

    @Override
    public boolean exists(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return wordsById.containsKey(id);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Word> findAll() {
        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return new ArrayList<>(wordsById.values());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public List<Word> findAll(Iterable<Long> ids) {
        assertArgumentNotNull(ids, "ids must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            final List<Word> result = new ArrayList<>();

            Word word;
            for (Long id : ids) {
                word = findOne(id);

                if (word != null) {
                    result.add(word);
                }
            }

            return result;
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public long count() {
        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return wordsById.size();
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public void delete(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            final Word deleteWord = wordsById.remove(id);
            if (deleteWord != null) {
                idsByChars.remove(deleteWord.getChars());

                for (WordCategory wordCategory : deleteWord.getCategories()) {
                    final Map<Long, Long> idxToIdMap = getIdxToIdMapForCategory(wordCategory);
                    final Map<Long, Long> idToIdxMap = getIdToIdxMapForCategory(wordCategory);

                    final Long idx = idToIdxMap.remove(id);
                    if (idx != null) {
                        idxToIdMap.remove(idx);
                        fillIndexWithLastValue(idx, idxToIdMap, idToIdxMap);
                    }
                }
            }
        } finally {
            writeLock.unlock();
        }
    }

    private void fillIndexWithLastValue(Long idx, Map<Long, Long> idxToIdMap, Map<Long, Long> idToIdxMap) {
        if (idxToIdMap.isEmpty() || idx == idxToIdMap.size()) {
            return;
        }

        final Long lastId = idxToIdMap.get((long)idxToIdMap.size());
        idxToIdMap.put(idx, lastId);
        idToIdxMap.put(lastId, idx);
    }

    @Override
    public void delete(Word word) {
        assertArgumentNotNull(word, "words must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();

            final Long wordId = idsByChars.get(word.getChars());
            if (wordId != null) {
                delete(wordId);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Iterable<? extends Word> words) {
        assertArgumentNotNull(words, "words must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();

            for (Word word : words) {
                delete(word);
            }
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void deleteAll() {
        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            wordsById.clear();
            idsByChars.clear();

            for (Map<Long, Long> idxToIdMap : idxToIdMapsForCategory) {
                idxToIdMap.clear();
            }

            for (Map<Long, Long> idToIdxMap : idToIdxMapsForCategory) {
                idToIdxMap.clear();
            }
        } finally {
            writeLock.unlock();
        }
    }
}
