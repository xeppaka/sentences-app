package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 *
 */
public interface RandomWordsProvider {
    Word getRandomWordForCategory(WordCategory wordCategory);
}
