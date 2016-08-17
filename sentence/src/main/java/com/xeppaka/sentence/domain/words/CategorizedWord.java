package com.xeppaka.sentence.domain.words;

import com.xeppaka.sentence.domain.BaseEntity;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.Set;

/**
 *
 */
public class CategorizedWord extends BaseEntity implements Word {
    private String chars;
    private Set<WordCategory> categories = EnumSet.noneOf(WordCategory.class);

    public CategorizedWord(String chars, WordCategory... categories) {
        assertArgumentNotEmpty(chars, "chars must not be null or empty.");
        assertArgumentNotEmpty(categories, "category must not be null or empty.");

        this.chars = chars;
        this.categories.addAll(Arrays.asList(categories));
    }

    public CategorizedWord(Word word) {
        assertArgumentNotNull(word, "word must not be null or empty.");

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
}
