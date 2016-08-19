package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;

/**
 *
 */
public class HumanThreeWordsSentence extends ThreeWordsSentence {
    public HumanThreeWordsSentence(Word firstWord, Word secondWord, Word thirdWord) {
        super(firstWord, secondWord, thirdWord);

        if (!firstWord.getCategories().contains(WordCategory.NOUN)) {
            throw new IllegalArgumentException("firstWord must be NOUN.");
        }

        if (!secondWord.getCategories().contains(WordCategory.VERB)) {
            throw new IllegalArgumentException("secondWord must be VERB.");
        }

        if (!thirdWord.getCategories().contains(WordCategory.OBJECTIVE)) {
            throw new IllegalArgumentException("thirdWord must be OBJECTIVE.");
        }
    }

    @Override
    public YodaThreeWordsSentence toYodaSentence() {
        return new YodaThreeWordsSentence(getWord(2), getWord(0), getWord(1));
    }
}
