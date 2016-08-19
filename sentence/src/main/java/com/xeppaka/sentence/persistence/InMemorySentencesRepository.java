package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.sentence.Sentence;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 */
@Repository
public class InMemorySentencesRepository extends AssertionConcern implements SentencesRepository {
    private static final AtomicLong nextId = new AtomicLong();

    private final Map<Long, Sentence> sentencesById = new HashMap<>();
    private final ReadWriteLock sync = new ReentrantReadWriteLock();

    @Override
    public <S extends Sentence> S save(S sentence) {
        assertArgumentNotNull(sentence, "entity must not be null.");

        if (!sentence.hasId()) {
            sentence.setId(nextId.getAndIncrement());
        }

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            sentencesById.put(sentence.getId(), sentence);
            return sentence;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public <S extends Sentence> Iterable<S> save(Iterable<S> sentences) {
        assertArgumentNotNull(sentences, "sentences must not be null.");

        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            final List<S> result = new ArrayList<>();

            for (S sentence : sentences) {
                result.add(save(sentence));
            }

            return result;
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public List<Sentence> findAll() {
        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return new ArrayList<>(sentencesById.values());
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Sentence findOne(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return sentencesById.get(id);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean exists(Long id) {
        assertArgumentNotNull(id, "id must not be null.");

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();
            return sentencesById.containsKey(id);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public Iterable<Sentence> findAll(Iterable<Long> ids) {
        assertArgumentNotNull(ids, "ids must not be null.");

        final List<Sentence> result = new ArrayList<>();

        final Lock readLock = sync.readLock();
        try {
            readLock.lock();

            Sentence sentence;
            for (Long id : ids) {
                sentence = findOne(id);

                if (sentence != null) {
                    result.add(sentence);
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
            return sentencesById.size();
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
            sentencesById.remove(id);
        } finally {
            writeLock.unlock();
        }
    }

    @Override
    public void delete(Sentence sentence) {
        throw new UnsupportedOperationException("Delete by entity value is not supported.");
    }

    @Override
    public void delete(Iterable<? extends Sentence> sentences) {
        throw new UnsupportedOperationException("Delete by entity value is not supported.");
    }

    @Override
    public void deleteAll() {
        final Lock writeLock = sync.writeLock();
        try {
            writeLock.lock();
            sentencesById.clear();
        } finally {
            writeLock.unlock();
        }
    }
}
