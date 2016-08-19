package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.Entity;
import com.xeppaka.sentence.domain.word.Word;

import java.time.LocalDateTime;

/**
 * Represents a sentence - an ordered sequence of word.
 */
public interface Sentence extends Entity {
    /**
     * Gets word from the sentence located at some index.
     * @param index is index of the word.
     * @return a word at index.
     */
    Word getWord(int index);

    /**
     * Gets number of word in the sentence.
     * @return number of word in the sentence.
     */
    int wordCount();

    LocalDateTime getGeneratedOn();

    int getViewCount();

    int increaseViewCount();

    Sentence toYodaSentence();

    String getText();
}
