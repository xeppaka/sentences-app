package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Repository
public class InMemoryWordsRepository extends AssertionConcern implements WordsRepository {
    private static final AtomicLong nextId = new AtomicLong();

    private final Map<Long, Word> wordsById = new HashMap<>();
    // helper for quick search over word value
    private final Map<String, Long> idsByChars = new HashMap<>();

    @Override
    public synchronized <S extends Word> S save(S word) {
        assertArgumentNotNull(word, "word must not be null.");

        final String chars = word.getChars().toLowerCase();
        Long idInDb = idsByChars.get(chars);
        if (idInDb == null) {
            idInDb = nextId.getAndIncrement();
        }

        word.setId(idInDb);
        idsByChars.put(chars, idInDb);
        wordsById.put(idInDb, word);

        return word;
    }

    @Override
    public synchronized <S extends Word> List<S> save(Iterable<S> words) {
        assertArgumentNotNull(words, "words must not be null.");

        final List<S> result = new ArrayList<>();

        for (S word : words) {
            result.add(save(word));
        }

        return result;
    }

    @Override
    public synchronized Word findOne(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        return wordsById.get(id);
    }

    @Override
    public synchronized Word findWord(String chars) {
        assertArgumentNotEmpty(chars, "chars must not be null or empty.");

        final Long id = idsByChars.get(chars.toLowerCase());
        if (id != null) {
            return wordsById.get(id);
        }

        return null;
    }

    @Override
    public Word findRandomWordForCategory(WordCategory wordCategory) {
        return null;
    }

    @Override
    public synchronized boolean exists(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        return wordsById.containsKey(id);
    }

    @Override
    public synchronized List<Word> findAll() {
        return new ArrayList<>(wordsById.values());
    }

    @Override
    public synchronized List<Word> findAll(Iterable<Long> ids) {
        assertArgumentNotNull(ids, "ids must not be null.");

        final List<Word> result = new ArrayList<>();

        Word word;
        for (Long id : ids) {
            word = findOne(id);

            if (word != null) {
                result.add(word);
            }
        }

        return result;
    }

    @Override
    public synchronized long count() {
        return wordsById.size();
    }

    @Override
    public synchronized void delete(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Word deleteWord = wordsById.remove(id);
        if (deleteWord != null) {
            idsByChars.remove(deleteWord.getChars());
        }
    }

    @Override
    public synchronized void delete(Word word) {
        assertArgumentNotNull(word, "word must not be null.");

        final Long wordId = idsByChars.get(word.getChars());
        if (wordId != null) {
            delete(wordId);
        }
    }

    @Override
    public synchronized void delete(Iterable<? extends Word> words) {
        assertArgumentNotNull(words, "word must not be null.");

        for (Word word : words) {
            delete(word);
        }
    }

    @Override
    public synchronized void deleteAll() {
        wordsById.clear();
        idsByChars.clear();
    }
}
