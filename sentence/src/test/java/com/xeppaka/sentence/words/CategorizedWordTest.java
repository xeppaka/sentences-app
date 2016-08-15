package com.xeppaka.sentence.words;

import org.junit.Assert;
import org.junit.Test;
import com.xeppaka.sentence.words.Word.WordCategory;

import java.util.Arrays;

/**
 *
 */
public class CategorizedWordTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCharsCannotBeNull() {
        new CategorizedWord(null, WordCategory.NOUN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCharsCannotBeNullWithId() {
        new CategorizedWord(0, null, WordCategory.NOUN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategotyCannotBeNull() {
        new CategorizedWord("Lexico", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategotyCannotBeNullWithId() {
        new CategorizedWord(0, "Lexico", null);
    }

    public void testConstructorSetsValues() {
        final Word word = new CategorizedWord(20, "Eubicor", WordCategory.VERB, WordCategory.NOUN);

        Assert.assertEquals(20, word.getId());
        Assert.assertEquals("Eubicor", word.getChars());
        Assert.assertEquals(2, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.NOUN)));
    }
}
