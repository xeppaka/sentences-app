package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;

import java.text.MessageFormat;

/**
 * Class represents word which contains always three words.
 */
public class ThreeWordsSentence extends BaseSentence implements Sentence {
    private final Word firstWord;
    private final Word secondWord;
    private final Word thirdWord;

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
                throw new IndexOutOfBoundsException(MessageFormat.format("There is no words with index {0} in the sentences.", index));
        }
    }

    @Override
    public int wordCount() {
        return 3;
    }

    @Override
    public Sentence toYodaSentence() {
        throw new UnsupportedOperationException("Converting general ThreeWordsSentence to YodaThreeWordsSentence is not supported.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ThreeWordsSentence that = (ThreeWordsSentence) o;

        if (!firstWord.equals(that.firstWord)) return false;
        if (!secondWord.equals(that.secondWord)) return false;
        return thirdWord.equals(that.thirdWord);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + firstWord.hashCode();
        result = 31 * result + secondWord.hashCode();
        result = 31 * result + thirdWord.hashCode();
        return result;
    }
}
