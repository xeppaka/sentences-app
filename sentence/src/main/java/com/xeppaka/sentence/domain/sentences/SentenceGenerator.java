package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;

/**
 * Interface represents sentence generator.
 */
public interface SentenceGenerator {
    /**
     * Generates new sentence.
     *
     * @return new generated sentence.
     * @throws NotEnoughWordsException if there are not enough words in the system to
     *                                 generate sentence.
     */
    Sentence generate() throws NotEnoughWordsException;
}
