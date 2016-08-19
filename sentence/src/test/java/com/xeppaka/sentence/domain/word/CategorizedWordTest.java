package com.xeppaka.sentence.domain.word;

import com.xeppaka.sentence.domain.word.Word.WordCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;

/**
 *
 */
public class CategorizedWordTest {
    @Test(expected = IllegalArgumentException.class)
    public void testCharsCannotBeNull() {
        new CategorizedWord(null, WordCategory.NOUN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategoriesCannotBeNull() {
        new CategorizedWord("Lexico", (WordCategory[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWordCategoriesCannotBeEmpty() {
        new CategorizedWord("Lexico");
    }

    @Test
    public void testConstructorSetsValues() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN);

        Assert.assertEquals("Eubicor", word.getChars());
        Assert.assertEquals(2, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.NOUN)));
    }

    @Test
    public void testAddCategory() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN);

        Assert.assertEquals(2, word.categoriesCount());
        word.addCategory(WordCategory.OBJECTIVE);
        Assert.assertEquals(3, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(EnumSet.allOf(WordCategory.class)));
    }

    @Test
    public void testAddSameCategory() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN);

        Assert.assertEquals(2, word.categoriesCount());
        word.addCategory(WordCategory.VERB);
        Assert.assertEquals(2, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.NOUN)));
        word.addCategory(WordCategory.OBJECTIVE);
        Assert.assertEquals(3, word.categoriesCount());
        word.addCategory(WordCategory.OBJECTIVE);
        Assert.assertEquals(3, word.categoriesCount());
        word.addCategory(WordCategory.VERB);
        Assert.assertEquals(3, word.categoriesCount());
        word.addCategory(WordCategory.NOUN);
        Assert.assertEquals(3, word.categoriesCount());
    }

    @Test
    public void testRemoveCategory() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN, WordCategory.OBJECTIVE);

        Assert.assertEquals(3, word.categoriesCount());
        word.removeCategory(WordCategory.NOUN);
        Assert.assertEquals(2, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.OBJECTIVE)));
        word.removeCategory(WordCategory.NOUN);
        Assert.assertEquals(2, word.categoriesCount());
        Assert.assertTrue(word.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.OBJECTIVE)));
        word.removeCategory(WordCategory.OBJECTIVE);
        Assert.assertEquals(1, word.categoriesCount());
        Assert.assertTrue(word.getCategories().contains(WordCategory.VERB));
    }

    @Test(expected = IllegalStateException.class)
    public void testRemoveLastCategoryIsNotAllowed() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN, WordCategory.OBJECTIVE);
        word.removeCategory(WordCategory.NOUN);
        word.removeCategory(WordCategory.OBJECTIVE);
        word.removeCategory(WordCategory.VERB);
    }

    @Test
    public void testRemoveLastCategoryIsNotThrowExceptionIfCategoryIsDifferent() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB, WordCategory.NOUN, WordCategory.OBJECTIVE);
        word.removeCategory(WordCategory.NOUN);
        word.removeCategory(WordCategory.OBJECTIVE);
        word.removeCategory(WordCategory.OBJECTIVE);
    }
}
