package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 *
 */
public class ThreeWordsSentenceGenerator extends AssertionConcern implements SentenceGenerator {
    private WordCategory firstCategory;
    private WordCategory secondCategory;
    private WordCategory thirdCategory;
    private RandomWordsProvider randomWordsProvider;

    public ThreeWordsSentenceGenerator(WordCategory firstCategory, WordCategory secondCategory, WordCategory thirdCategory, RandomWordsProvider randomWordsProvider) {
        assertArgumentNotNull(firstCategory, "firstCategory must not be null.");
        assertArgumentNotNull(secondCategory, "secondCategory must not be null.");
        assertArgumentNotNull(thirdCategory, "thirdCategory must not be null.");
        assertArgumentNotNull(randomWordsProvider, "randomWordsProvider must not be null.");

        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.randomWordsProvider = randomWordsProvider;
    }

    @Override
    public Sentence generate() throws NotEnoughWordsException {
        final Word firstWord = randomWordsProvider.getRandomWordForCategory(firstCategory);
        if (firstWord == null) {
            throw new NotEnoughWordsException();
        }

        final Word secondWord = randomWordsProvider.getRandomWordForCategory(secondCategory);
        if (secondWord == null) {
            throw new NotEnoughWordsException();
        }

        final Word thirdWord = randomWordsProvider.getRandomWordForCategory(thirdCategory);
        if (thirdWord == null) {
            throw new NotEnoughWordsException();
        }

        return new ThreeWordsSentence(firstWord, secondWord, thirdWord);
    }
}
