package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.sentence.Sentence;
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
public class InMemorySentencesRepository extends AssertionConcern implements SentencesRepository {
    private static final AtomicLong nextId = new AtomicLong();

    private final Map<Long, Sentence> sentencesById = new HashMap<>();

    @Override
    public synchronized <S extends Sentence> S save(S sentence) {
        assertArgumentNotNull(sentence, "entity must not be null.");

        if (!sentence.hasId()) {
            sentence.setId(nextId.getAndIncrement());
        }

        sentencesById.put(sentence.getId(), sentence);
        return sentence;
    }

    @Override
    public synchronized <S extends Sentence> Iterable<S> save(Iterable<S> sentences) {
        assertArgumentNotNull(sentences, "sentences must not be null.");

        final List<S> result = new ArrayList<>();

        for (S sentence : sentences) {
            result.add(save(sentence));
        }

        return result;
    }

    @Override
    public synchronized List<Sentence> findAll() {
        return new ArrayList<>(sentencesById.values());
    }

    @Override
    public synchronized Sentence findOne(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        return sentencesById.get(id);
    }

    @Override
    public synchronized boolean exists(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        return sentencesById.containsKey(id);
    }

    @Override
    public synchronized Iterable<Sentence> findAll(Iterable<Long> ids) {
        assertArgumentNotNull(ids, "ids must not be null.");

        final List<Sentence> result = new ArrayList<>();

        Sentence sentence;
        for (Long id : ids) {
            sentence = findOne(id);

            if (sentence != null) {
                result.add(sentence);
            }
        }

        return result;
    }

    @Override
    public synchronized long count() {
        return sentencesById.size();
    }

    @Override
    public synchronized void delete(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        sentencesById.remove(id);
    }

    @Override
    public synchronized void delete(Sentence sentence) {
        throw new UnsupportedOperationException("Delete by entity value is not supported.");
    }

    @Override
    public synchronized void delete(Iterable<? extends Sentence> sentences) {
        throw new UnsupportedOperationException("Delete by entity value is not supported.");
    }

    @Override
    public synchronized void deleteAll() {
        sentencesById.clear();
    }
}
