package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 * Interface represents provider of random words for specific category.
 */
public interface RandomWordsProvider {
    /**
     * Gets random word for specific category.
     * @param wordCategory is category of which word should be returned.
     * @return word with specified category.
     */
    Word getRandomWordForCategory(WordCategory wordCategory);
}
