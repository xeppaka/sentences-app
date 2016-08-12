package com.xeppaka.sentence.words;

/**
 *
 */
public class SimpleWord implements Word {
    private long id;
    private String chars;
    private WordCategory category;

    public SimpleWord(long id, String chars, WordCategory category) {
        validate(id, chars, category);

        this.id = id;
        this.chars = chars;
        this.category = category;
    }

    private void validate(long id, String chars, WordCategory category) {
        if (id < 0) {
            throw new IllegalArgumentException("id must be positive.");
        }

        if (chars == null || chars.isEmpty()) {
            throw new IllegalArgumentException("chars must not be null or empty.");
        }

        if (category == null) {
            throw new IllegalArgumentException("category must not be null.");
        }
    }

    @Override
    public long getId() {
        return id;
    }

    public String getChars() {
        return chars;
    }

    public WordCategory getCategory() {
        return category;
    }
}
