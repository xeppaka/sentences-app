package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.CategorizedWord;
import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class HumanThreeWordsSentenceTest {
    private Word noun;
    private Word verb;
    private Word objective;

    @Before
    public void setUp() {
        verb = new CategorizedWord("Dell", WordCategory.VERB);
        noun = new CategorizedWord("Lenovo", WordCategory.NOUN);
        objective = new CategorizedWord("Gigabyte", WordCategory.OBJECTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor1() {
        new HumanThreeWordsSentence(verb, verb, objective);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor2() {
        new HumanThreeWordsSentence(noun, noun, objective);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor3() {
        new HumanThreeWordsSentence(noun, verb, verb);
    }

    @Test
    public void testProperConstructor() {
        final Sentence sentence = new HumanThreeWordsSentence(noun, verb, objective);
        Assert.assertEquals(noun, sentence.getWord(0));
        Assert.assertEquals(verb, sentence.getWord(1));
        Assert.assertEquals(objective, sentence.getWord(2));
    }

    @Test
    public void testToYodaSentenceConversion() {
        final Sentence sentence = new HumanThreeWordsSentence(noun, verb, objective);
        final Sentence yodaSentence = sentence.toYodaSentence();

        Assert.assertEquals(objective, yodaSentence.getWord(0));
        Assert.assertEquals(noun, yodaSentence.getWord(1));
        Assert.assertEquals(verb, yodaSentence.getWord(2));
    }
}
