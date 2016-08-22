package com.xeppaka.sentence.domain.words;

import com.xeppaka.sentence.domain.BaseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

/**
 * Implementation of {@link Word} interface with ability to set word categories (NOUN, VERB, ADJECTIVE).
 */
public class CategorizedWord extends BaseEntity implements Word {
    private final String chars;
    private final Set<WordCategory> categories = Collections.synchronizedSet(EnumSet.noneOf(WordCategory.class));

    /**
     * Default word constructor.
     *
     * @param chars      is the word itself.
     * @param categories is the word categories. There should be at least 1 category, otherwise
     *                   {@link IllegalArgumentException} exception is thrown.
     *
     */
    public CategorizedWord(String chars, WordCategory... categories) {
        assertArgumentNotEmpty(chars, "chars must not be null or empty.");
        assertArgumentNotEmpty(categories, "category must not be null or empty.");

        this.chars = chars;
        this.categories.addAll(Arrays.asList(categories));
    }

    /**
     * Copy constructor.
     *
     * @param word is the word from which values are copied.
     */
    public CategorizedWord(Word word) {
        assertArgumentNotNull(word, "words must not be null or empty.");

        this.chars = word.getChars();
        this.categories.addAll(word.getCategories());
    }

    @Override
    public String getChars() {
        return chars;
    }

    @Override
    public Set<WordCategory> getCategories() {
        return categories;
    }

    @Override
    public int categoriesCount() {
        return getCategories().size();
    }

    @Override
    public void addCategory(WordCategory category) {
        assertArgumentNotNull(category, "category must not be null.");

        getCategories().add(category);
    }

    @Override
    public void removeCategory(WordCategory category) {
        assertArgumentNotNull(category, "category must not be null.");

        if (categoriesCount() == 1 && categories.contains(category)) {
            throw new IllegalStateException("Cannot remove last category. Word should have 1 category at minimum.");
        }

        getCategories().remove(category);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CategorizedWord that = (CategorizedWord) o;

        if (!chars.equals(that.chars)) return false;
        return categories.equals(that.categories);

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + chars.hashCode();
        result = 31 * result + categories.hashCode();
        return result;
    }
}
