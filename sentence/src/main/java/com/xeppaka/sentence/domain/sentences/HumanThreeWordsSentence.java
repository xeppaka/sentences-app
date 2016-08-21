package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 * Class represents sentence in a human form (NOUN, VERB, ADJECTIVE).
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

        if (!thirdWord.getCategories().contains(WordCategory.ADJECTIVE)) {
            throw new IllegalArgumentException("thirdWord must be ADJECTIVE.");
        }
    }

    public HumanThreeWordsSentence(Sentence sentence) {
        this(sentence.getWord(0), sentence.getWord(1), sentence.getWord(2));
    }

    @Override
    public YodaThreeWordsSentence toYodaSentence() {
        return new YodaThreeWordsSentence(getWord(0), getWord(2), getWord(1));
    }
}
