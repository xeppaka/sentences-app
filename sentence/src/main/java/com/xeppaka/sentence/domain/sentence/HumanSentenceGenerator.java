package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.Word.WordCategory;

/**
 *
 */
public class HumanSentenceGenerator extends ThreeWordsSentenceGenerator {
    public HumanSentenceGenerator(RandomWordsProvider randomWordsProvider) {
        super(WordCategory.NOUN, WordCategory.VERB, WordCategory.OBJECTIVE, randomWordsProvider);
    }
}
