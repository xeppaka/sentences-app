package com.xeppaka.sentence.domain.word;

import com.xeppaka.sentence.domain.Entity;

import java.util.Set;

/**
 * Represents Word contract.
 */
public interface Word extends Entity {
    enum WordCategory {
        NOUN,
        VERB,
        OBJECTIVE
    }

    /**
     * Gets word as sequence of chars.
     * @return String with chars of the word.
     */
    String getChars();

    /**
     * Gets set of categories a word belongs to.
     * @return set of categories
     */
    Set<WordCategory> getCategories();

    /**
     * Adds new category to which word belongs to.
     * @param category is category to add.
     */
    void addCategory(WordCategory category);

    /**
     * Removes category from the word.
     * If word has only one category it cannot be removed.
     * @param category is category to remove.
     */
    void removeCategory(WordCategory category);

    /**
     * Gets number of categories a word belongs to.
     * @return number of categories a word belongs to.
     */
    int categoriesCount();
}
