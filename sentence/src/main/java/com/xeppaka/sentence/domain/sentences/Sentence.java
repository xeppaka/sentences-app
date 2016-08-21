package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.Entity;
import com.xeppaka.sentence.domain.words.Word;

import java.time.LocalDateTime;

/**
 * Represents a sentences - an ordered sequence of words.
 */
public interface Sentence extends Entity {
    /**
     * Gets words from the sentences located at some index.
     * @param index is index of the words.
     * @return a words at index.
     */
    Word getWord(int index);

    /**
     * Gets number of words in the sentences.
     * @return number of words in the sentences.
     */
    int wordCount();

    LocalDateTime getGeneratedOn();

    int getViewCount();

    int increaseViewCount();

    Sentence toYodaSentence();

    String getText();
}
