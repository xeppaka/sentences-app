package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.sentence.exceptions.NotEnoughWordsException;

/**
 *
 */
public interface SentenceGenerator {
    Sentence generate() throws NotEnoughWordsException;
}
