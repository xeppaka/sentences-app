package com.xeppaka.sentence.words;

import com.xeppaka.sentence.BaseEntity;

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
        validate(chars, categories);

        this.chars = chars;
        this.categories.addAll(Arrays.asList(categories));
    }

    public CategorizedWord(long id, String chars, WordCategory... categories) {
        super(id);

        validate(chars, categories);

        this.chars = chars;
        this.categories.addAll(Arrays.asList(categories));
    }

    private void validate(String chars, WordCategory... categories) {
        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("chars must not be null or empty.");
        }

        if (categories == null || categories.length <= 0) {
            throw new IllegalArgumentException("category must not be null or empty.");
        }
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
        if (category == null) {
            throw new IllegalArgumentException("");
        }

        getCategories().add(category);
    }

    @Override
    public void removeCategory(WordCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("");
        }

        if (categoriesCount() <= 1) {
            throw new IllegalStateException("");
        }

        getCategories().remove(category);
    }
}
