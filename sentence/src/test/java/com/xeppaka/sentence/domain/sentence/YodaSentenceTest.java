package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.CategorizedWord;
import com.xeppaka.sentence.domain.word.Word;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class YodaSentenceTest {
    private Word noun;
    private Word verb;
    private Word objective;

    @Before
    public void setUp() {
        verb = new CategorizedWord("Dell", Word.WordCategory.VERB);
        noun = new CategorizedWord("Lenovo", Word.WordCategory.NOUN);
        objective = new CategorizedWord("Gigabyte", Word.WordCategory.OBJECTIVE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor1() {
        new YodaSentence(verb, noun, verb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor2() {
        new YodaSentence(objective, verb, verb);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordsCategoriesSequenceInConstructor3() {
        new YodaSentence(objective, noun, noun);
    }

    @Test
    public void testProperConstructor() {
        final Sentence sentence = new YodaSentence(objective, noun, verb);
        Assert.assertEquals(objective, sentence.getWord(0));
        Assert.assertEquals(noun, sentence.getWord(1));
        Assert.assertEquals(verb, sentence.getWord(2));
    }

    @Test
    public void testToYodaSentenceConversion() {
        final Sentence sentence = new YodaSentence(objective, noun, verb);
        final Sentence yodaSentence = sentence.toYodaSentence();

        Assert.assertEquals(objective, yodaSentence.getWord(0));
        Assert.assertEquals(noun, yodaSentence.getWord(1));
        Assert.assertEquals(verb, yodaSentence.getWord(2));
    }
}
