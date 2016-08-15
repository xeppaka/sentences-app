package com.xeppaka.sentence.words;

import com.xeppaka.sentence.BaseEntity;

/**
 *
 */
public class WordInCategory extends BaseEntity implements Word {
    private String chars;
    private WordCategory category;

    public WordInCategory(String chars, WordCategory category) {
        validate(chars, category);

        this.chars = chars;
        this.category = category;
    }

    public WordInCategory(long id, String chars, WordCategory category) {
        super(id);

        validate(chars, category);

        this.chars = chars;
        this.category = category;
    }

    private void validate(String chars, WordCategory category) {
        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("chars must not be null or empty.");
        }

        if (category == null) {
            throw new IllegalArgumentException("category must not be null.");
        }
    }

    public String getChars() {
        return chars;
    }

    public WordCategory getCategory() {
        return category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        WordInCategory that = (WordInCategory) o;

        if (!chars.equals(that.chars)) return false;
        return category == that.category;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + chars.hashCode();
        result = 31 * result + category.hashCode();
        return result;
    }
}
