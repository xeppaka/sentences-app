package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class YodaThreeWordsSentenceTest {
    private Word noun;
    private Word verb;
    private Word adjective;

    @Before
    public void setUp() {
        verb = new CategorizedWord("Dell", Word.WordCategory.VERB);
        noun = new CategorizedWord("Lenovo", Word.WordCategory.NOUN);
        adjective = new CategorizedWord("Gigabyte", Word.WordCategory.ADJECTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor1() {
        new YodaThreeWordsSentence(verb, noun, verb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor2() {
        new YodaThreeWordsSentence(adjective, verb, verb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor3() {
        new YodaThreeWordsSentence(adjective, noun, noun);
    }

    @Test
    public void testProperConstructor() {
        final Sentence sentence = new YodaThreeWordsSentence(noun, adjective, verb);
        Assert.assertEquals(noun, sentence.getWord(0));
        Assert.assertEquals(adjective, sentence.getWord(1));
        Assert.assertEquals(verb, sentence.getWord(2));
    }

    @Test
    public void testToYodaSentenceConversion() {
        final Sentence sentence = new YodaThreeWordsSentence(noun, adjective, verb);
        final Sentence yodaSentence = sentence.toYodaSentence();

        Assert.assertEquals(noun, yodaSentence.getWord(0));
        Assert.assertEquals(adjective, yodaSentence.getWord(1));
        Assert.assertEquals(verb, yodaSentence.getWord(2));
    }
}
