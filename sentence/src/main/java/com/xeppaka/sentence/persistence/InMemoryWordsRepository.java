package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.words.Word;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 */
@Repository
public class InMemoryWordsRepository implements WordsRepository {
    private static AtomicLong nextId = new AtomicLong();

    private Map<Long, Word> wordsById = new HashMap<>();
    private Map<String, Word> wordsByChars = new HashMap<>();

    @Override
    public <S extends Word> S save(S entity) {
        if (entity == null) {
            throw new IllegalArgumentException("entity must not be null.");
        }

        final long id = nextId.getAndIncrement();
        wordsById.put(id, entity);
        wordsByChars.put(entity.getChars(), entity);

        return entity;
    }

    @Override
    public <S extends Word> List<S> save(Iterable<S> entities) {
        if (entities == null) {
            throw new IllegalArgumentException("entities must not be null.");
        }

        final List<S> result = new ArrayList<>();

        for (S entity : entities) {
            result.add(save(entity));
        }

        return result;
    }

    @Override
    public Word findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        return wordsById.get(id);
    }

    @Override
    public Word findByChars(String chars) {
        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("chars must not be null or empty.");
        }

        return wordsByChars.get(chars);
    }

    @Override
    public boolean exists(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        return wordsById.containsKey(id);
    }

    @Override
    public List<Word> findAll() {
        return new ArrayList<>(wordsById.values());
    }

    @Override
    public List<Word> findAll(Iterable<Long> ids) {
        if (ids == null) {
            throw new IllegalArgumentException("ids must not be null.");
        }

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
    public long count() {
        return wordsById.size();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("id must not be null.");
        }

        final Word deleteWord = wordsById.get(id);

        if (deleteWord != null) {
            wordsByChars.remove(deleteWord.getChars());
        }

        wordsById.remove(id);
    }

    @Override
    public void delete(Word word) {
        if (word == null) {
            throw new IllegalArgumentException("word must not be null.");
        }

        delete(word.getId());
    }

    @Override
    public void delete(Iterable<? extends Word> words) {
        if (words == null) {
            throw new IllegalArgumentException("words must not be null.");
        }

        for (Word word : words) {
            delete(word);
        }
    }

    @Override
    public void deleteAll() {
        wordsById.clear();
        wordsByChars.clear();
    }
}
