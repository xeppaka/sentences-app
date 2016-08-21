package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;

/**
 *
 */
public interface SentenceGenerator {
    Sentence generate() throws NotEnoughWordsException;
}
