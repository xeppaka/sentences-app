package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 * Class implements sentence generator which generates sentence of three words each with specific category.
 */
public class ThreeWordsSentenceGenerator extends AssertionConcern implements SentenceGenerator {
    private final WordCategory firstCategory;
    private final WordCategory secondCategory;
    private final WordCategory thirdCategory;
    private final RandomWordsProvider randomWordsProvider;

    /**
     * Generator constructor.
     *
     * @param firstWordCategory is category that should have first word of the generated sentence.
     * @param secondWordCategory is category that should have second word of the generated sentence.
     * @param thirdWordCategory is category that should have third word of the generated sentence.
     * @param randomWordsProvider is random words provided.
     */
    public ThreeWordsSentenceGenerator(WordCategory firstWordCategory, WordCategory secondWordCategory, WordCategory thirdWordCategory, RandomWordsProvider randomWordsProvider) {
        assertArgumentNotNull(firstWordCategory, "firstWordCategory must not be null.");
        assertArgumentNotNull(secondWordCategory, "secondWordCategory must not be null.");
        assertArgumentNotNull(thirdWordCategory, "thirdWordCategory must not be null.");
        assertArgumentNotNull(randomWordsProvider, "randomWordsProvider must not be null.");

        this.firstCategory = firstWordCategory;
        this.secondCategory = secondWordCategory;
        this.thirdCategory = thirdWordCategory;
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
