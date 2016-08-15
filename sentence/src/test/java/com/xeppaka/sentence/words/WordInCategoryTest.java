package com.xeppaka.sentence.words;

import org.junit.Assert;
import org.junit.Test;
import com.xeppaka.sentence.words.Word.WordCategory;

/**
 *
 */
public class WordInCategoryTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCharsCannotBeNull() {
        new WordInCategory(null, WordCategory.NOUN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCharsCannotBeNullWithId() {
        new WordInCategory(0, null, WordCategory.NOUN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategotyCannotBeNull() {
        new WordInCategory("Lexico", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategotyCannotBeNullWithId() {
        new WordInCategory(0, "Lexico", null);
    }

    public void testConstructorSetsValues() {
        final Word word = new WordInCategory(20, "Eubicor", WordCategory.VERB);

        Assert.assertEquals(20, word.getId());
        Assert.assertEquals("Eubicor", word.getChars());
        Assert.assertEquals(WordCategory.VERB, word.getCategory());
    }
}
