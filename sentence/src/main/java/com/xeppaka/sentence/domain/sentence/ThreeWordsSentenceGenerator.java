package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.word.Word.WordCategory;

/**
 *
 */
public class ThreeWordsSentenceGenerator extends AssertionConcern implements SentenceGenerator {
    private WordCategory firstCategory;
    private WordCategory secondCategory;
    private WordCategory thirdCategory;
    private RandomWordsSource randomWordsSource;

    public ThreeWordsSentenceGenerator(WordCategory firstCategory, WordCategory secondCategory, WordCategory thirdCategory, RandomWordsSource randomWordsSource) {
        assertArgumentNotNull(firstCategory, "firstCategory must not be null.");
        assertArgumentNotNull(secondCategory, "secondCategory must not be null.");
        assertArgumentNotNull(thirdCategory, "thirdCategory must not be null.");
        assertArgumentNotNull(randomWordsSource, "randomWordsSource must not be null.");

        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.randomWordsSource = randomWordsSource;
    }

    @Override
    public Sentence generate() {
        return new ThreeWordsSentence(randomWordsSource.getRandomWordForCategory(firstCategory),
                randomWordsSource.getRandomWordForCategory(secondCategory),
                randomWordsSource.getRandomWordForCategory(thirdCategory));
    }
}
