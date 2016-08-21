package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;

/**
 * Class represents sentence in a Yoda form (NOUN, ADJECTIVE, VERB).
 */
public class YodaThreeWordsSentence extends ThreeWordsSentence {
    public YodaThreeWordsSentence(Word firstWord, Word secondWord, Word thirdWord) {
        super(firstWord, secondWord, thirdWord);

        if (!firstWord.getCategories().contains(Word.WordCategory.NOUN)) {
            throw new IllegalArgumentException("firstWord must be NOUN.");
        }

        if (!secondWord.getCategories().contains(Word.WordCategory.ADJECTIVE)) {
            throw new IllegalArgumentException("secondWord must be ADJECTIVE.");
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
