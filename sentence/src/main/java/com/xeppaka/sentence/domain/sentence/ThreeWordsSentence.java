package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.BaseEntity;
import com.xeppaka.sentence.domain.word.Word;

import java.text.MessageFormat;

/**
 *
 */
public class ThreeWordsSentence extends BaseSentence implements Sentence {
    private Word firstWord;
    private Word secondWord;
    private Word thirdWord;

    public ThreeWordsSentence(Word firstWord, Word secondWord, Word thirdWord) {
        assertArgumentNotNull(firstWord, "firstWord must not be null.");
        assertArgumentNotNull(secondWord, "secondWord must not be null.");
        assertArgumentNotNull(thirdWord, "thirdWord must not be null.");

        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    @Override
    public Word getWord(int index) {
        switch (index) {
            case 0:
                return firstWord;
            case 1:
                return secondWord;
            case 2:
                return thirdWord;
            default:
                throw new IndexOutOfBoundsException(MessageFormat.format("There is no word with index {0} in the sentence.", index));
        }
    }

    @Override
    public int wordCount() {
        return 3;
    }
}
