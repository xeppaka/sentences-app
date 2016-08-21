package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.Entity;
import com.xeppaka.sentence.domain.words.Word;

import java.time.LocalDateTime;

/**
 * Represents a sentence - an ordered sequence of words.
 */
public interface Sentence extends Entity {
    /**
     * Gets words from the sentence located at some index.
     * @param index is index of the words.
     * @return a words at index.
     */
    Word getWord(int index);

    /**
     * Gets number of words in the sentence.
     * @return number of words in the sentence.
     */
    int wordCount();

    /**
     * Gets date and time when sentence was generated.
     * @return date and time when sentence was generated.
     */
    LocalDateTime getGeneratedOn();

    /**
     * Gets number of views of the sentence.
     * @return number of views of the sentence.
     */
    int getViewCount();

    /**
     * Increases number of views of the sentence.
     * @return new (after increase) number of views of the sentence.
     */
    int increaseViewCount();

    /**
     * Converts sentence to Yoda form of the sentence (NOUN ADJECTIVE VERB).
     * @return sentence in Yoda form.
     */
    Sentence toYodaSentence();

    /**
     * Gets sentence text.
     * @return sentence text - sequence of words divided by space.
     */
    String getText();
}
