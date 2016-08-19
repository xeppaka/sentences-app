package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;

/**
 *
 */
public interface RandomWordsSource {
    Word getRandomWordForCategory(WordCategory wordCategory);
}
