package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.sentence.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.word.CategorizedWord;
import com.xeppaka.sentence.domain.word.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ThreeWordsSentenceGeneratorTest {
    private RandomWordsProvider sameWordSource;

    @Before
    public void setUp() {
        sameWordSource = (category) -> new CategorizedWord("Eubicor", category);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgument1CannotBeNull() {
        new ThreeWordsSentenceGenerator(null, WordCategory.NOUN, WordCategory.VERB, sameWordSource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgument2CannotBeNull() {
        new ThreeWordsSentenceGenerator(WordCategory.OBJECTIVE, null, WordCategory.VERB, sameWordSource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgument3CannotBeNull() {
        new ThreeWordsSentenceGenerator(WordCategory.OBJECTIVE, WordCategory.NOUN, null, sameWordSource);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorArgument4CannotBeNull() {
        new ThreeWordsSentenceGenerator(WordCategory.OBJECTIVE, WordCategory.NOUN, WordCategory.VERB, null);
    }

    @Test
    public void testSentenceGeneratedWithProvidedCategory() throws NotEnoughWordsException {
        final SentenceGenerator sentenceGenerator = new ThreeWordsSentenceGenerator(WordCategory.OBJECTIVE, WordCategory.NOUN, WordCategory.VERB, sameWordSource);
        final Sentence sentence = sentenceGenerator.generate();

        Assert.assertEquals(3, sentence.wordCount());
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.OBJECTIVE), sentence.getWord(0));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.NOUN), sentence.getWord(1));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.VERB), sentence.getWord(2));
    }

    @Test
    public void testSentenceGeneratedWithSameCategory() throws NotEnoughWordsException {
        final SentenceGenerator sentenceGenerator = new ThreeWordsSentenceGenerator(WordCategory.OBJECTIVE, WordCategory.OBJECTIVE, WordCategory.OBJECTIVE, sameWordSource);
        final Sentence sentence = sentenceGenerator.generate();

        Assert.assertEquals(3, sentence.wordCount());
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.OBJECTIVE), sentence.getWord(0));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.OBJECTIVE), sentence.getWord(1));
        Assert.assertEquals(sameWordSource.getRandomWordForCategory(WordCategory.OBJECTIVE), sentence.getWord(2));
    }
}
