package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.sentences.Sentence;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class InMemorySentencesRepositoryTest {
    private SentencesRepository sentencesRepository;

    @Before
    public void setUp() {
        sentencesRepository = new InMemorySentencesRepository();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveNullThrowsException() {
        sentencesRepository.save((Sentence) null);
    }
}
