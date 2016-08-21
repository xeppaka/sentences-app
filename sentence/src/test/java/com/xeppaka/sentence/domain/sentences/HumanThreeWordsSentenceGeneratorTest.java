package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class HumanThreeWordsSentenceGeneratorTest {
    private RandomWordsProvider sameWordSource;

    @Before
    public void setUp() {
        sameWordSource = (category) -> new CategorizedWord("Eubicor", category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgumentCannotBeNull() {
        new HumanSentenceGenerator(null);
    }

    @Test
    public void testSentenceGeneratedWithProvidedCategory() throws NotEnoughWordsException {
        final SentenceGenerator sentenceGenerator = new HumanSentenceGenerator(sameWordSource);
        final Sentence sentence = sentenceGenerator.generate();

        Assert.assertEquals(3, sentence.wordCount());
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(Word.WordCategory.NOUN), sentence.getWord(0));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(Word.WordCategory.VERB), sentence.getWord(1));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(Word.WordCategory.ADJECTIVE), sentence.getWord(2));
    }
}
