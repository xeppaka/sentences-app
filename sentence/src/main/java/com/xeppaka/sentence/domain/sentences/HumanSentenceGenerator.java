package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

/**
 * Class represents generator of a sentence in human form (NOUN, VERB, ADJECTIVE).
 */
public class HumanSentenceGenerator extends ThreeWordsSentenceGenerator {
    public HumanSentenceGenerator(RandomWordsProvider randomWordsProvider) {
        super(WordCategory.NOUN, WordCategory.VERB, WordCategory.ADJECTIVE, randomWordsProvider);
    }

    @Override
    public Sentence generate() throws NotEnoughWordsException {
        return new HumanThreeWordsSentence(super.generate());
    }
}
