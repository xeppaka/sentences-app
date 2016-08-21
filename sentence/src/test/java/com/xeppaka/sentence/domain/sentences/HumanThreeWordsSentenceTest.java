package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class HumanThreeWordsSentenceTest {
    private Word noun;
    private Word verb;
    private Word adjective;

    @Before
    public void setUp() {
        verb = new CategorizedWord("Dell", WordCategory.VERB);
        noun = new CategorizedWord("Lenovo", WordCategory.NOUN);
        adjective = new CategorizedWord("Gigabyte", WordCategory.ADJECTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor1() {
        new HumanThreeWordsSentence(verb, verb, adjective);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor2() {
        new HumanThreeWordsSentence(noun, noun, adjective);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor3() {
        new HumanThreeWordsSentence(noun, verb, verb);
    }

    @Test
    public void testProperConstructor() {
        final Sentence sentence = new HumanThreeWordsSentence(noun, verb, adjective);
        Assert.assertEquals(noun, sentence.getWord(0));
        Assert.assertEquals(verb, sentence.getWord(1));
        Assert.assertEquals(adjective, sentence.getWord(2));
    }

    @Test
    public void testToYodaSentenceConversion() {
        final Sentence sentence = new HumanThreeWordsSentence(noun, verb, adjective);
        final Sentence yodaSentence = sentence.toYodaSentence();

        Assert.assertEquals(noun, yodaSentence.getWord(0));
        Assert.assertEquals(adjective, yodaSentence.getWord(1));
        Assert.assertEquals(verb, yodaSentence.getWord(2));
    }
}
