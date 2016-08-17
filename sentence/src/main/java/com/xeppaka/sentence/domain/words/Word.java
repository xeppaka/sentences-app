package com.xeppaka.sentence.domain.words;

import com.xeppaka.sentence.domain.Entity;

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
