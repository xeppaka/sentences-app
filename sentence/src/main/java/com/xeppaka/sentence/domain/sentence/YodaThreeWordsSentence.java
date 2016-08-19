package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.Word;

/**
 *
 */
public class YodaThreeWordsSentence extends ThreeWordsSentence {
    public YodaThreeWordsSentence(Word firstWord, Word secondWord, Word thirdWord) {
        super(firstWord, secondWord, thirdWord);

        if (!firstWord.getCategories().contains(Word.WordCategory.OBJECTIVE)) {
            throw new IllegalArgumentException("firstWord must be OBJECTIVE.");
        }

        if (!secondWord.getCategories().contains(Word.WordCategory.NOUN)) {
            throw new IllegalArgumentException("secondWord must be NOUN.");
        }

        if (!thirdWord.getCategories().contains(Word.WordCategory.VERB)) {
            throw new IllegalArgumentException("thirdWord must be VERBs.");
        }
    }

    @Override
    public YodaThreeWordsSentence toYodaSentence() {
        return this;
    }
}
