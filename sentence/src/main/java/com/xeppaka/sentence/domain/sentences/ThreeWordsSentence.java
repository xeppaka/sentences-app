package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;

import java.text.MessageFormat;

/**
 *
 */
public class ThreeWordsSentence implements Sentence {
    private Word firstWord;
    private Word secondWord;
    private Word thirdWord;

    public ThreeWordsSentence(Word firstWord, Word secondWord, Word thirdWord) {
        validate(firstWord, secondWord, thirdWord);

        this.firstWord = firstWord;
        this.secondWord = secondWord;
        this.thirdWord = thirdWord;
    }

    private void validate(Word firstWord, Word secondWord, Word thirdWord) {
        if (firstWord == null) {
            throw new IllegalArgumentException("firstWord cannot be null.");
        }

        if (secondWord == null) {
            throw new IllegalArgumentException("secondWord cannot be null.");
        }

        if (thirdWord == null) {
            throw new IllegalArgumentException("thirdWord cannot be null.");
        }
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
