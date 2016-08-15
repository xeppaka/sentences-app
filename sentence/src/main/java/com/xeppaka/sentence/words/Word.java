package com.xeppaka.sentence.words;

import com.xeppaka.sentence.Entity;

import java.util.Set;

/**
 *
 */
public interface Word extends Entity {
    enum WordCategory {
        NOUN,
        VERB,
        OBJECTIVE
    }

    String getChars();
    Set<WordCategory> getCategories();
    void addCategory(WordCategory category);
    void removeCategory(WordCategory category);
    int categoriesCount();
}
