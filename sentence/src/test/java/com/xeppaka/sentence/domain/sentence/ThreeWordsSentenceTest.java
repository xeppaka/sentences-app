package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.word.CategorizedWord;
import com.xeppaka.sentence.domain.word.Word;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 */
public class ThreeWordsSentenceTest {
    private Word[] words = new Word[]{
        new CategorizedWord("table", Word.WordCategory.NOUN),
        new CategorizedWord("green", Word.WordCategory.OBJECTIVE),
        new CategorizedWord("stay", Word.WordCategory.VERB)
    };

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDoesntAllowNull1() {
        new ThreeWordsSentence(null, words[0], words[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDoesntAllowNull2() {
        new ThreeWordsSentence(words[0], null, words[1]);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorDoesntAllowNull3() {
        new ThreeWordsSentence(words[0], words[1], null);
    }

    @Test
    public void testGetWordReturnsCorrectWord() {
        final Sentence sentence = new ThreeWordsSentence(words[0], words[1], words[2]);
        Assert.assertEquals(words[0], sentence.getWord(0));
        Assert.assertEquals(words[1], sentence.getWord(1));
        Assert.assertEquals(words[2], sentence.getWord(2));
    }

    @Test
    public void testGetWordsCountIsThree() {
        final Sentence sentence = new ThreeWordsSentence(words[0], words[1], words[2]);
        Assert.assertEquals(3, sentence.wordCount());
    }
}
