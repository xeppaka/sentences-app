package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.AssertionConcern;
import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;

/**
 *
 */
public class ThreeWordsSentenceGenerator extends AssertionConcern implements SentenceGenerator {
    private WordCategory firstCategory;
    private WordCategory secondCategory;
    private WordCategory thirdCategory;

    public ThreeWordsSentenceGenerator(WordCategory firstCategory, WordCategory secondCategory, WordCategory thirdCategory) {
        assertArgumentNotNull(firstCategory, "firstCategory must not be null.");
        assertArgumentNotNull(secondCategory, "secondCategory must not be null.");
        assertArgumentNotNull(thirdCategory, "thirdCategory must not be null.");

        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
    }

    @Override
    public Sentence generate() {
        return null;
    }
}
