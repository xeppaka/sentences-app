package com.xeppaka.sentence.domain.words;

import com.xeppaka.sentence.domain.Entity;

import java.util.Set;

/**
 * Interface represents Word contract.
 */
public interface Word extends Entity {
    enum WordCategory {
        NOUN,
        VERB,
        ADJECTIVE
    }

    /**
     * Gets words as sequence of chars.
     * @return String with chars of the words.
     */
    String getChars();

    /**
     * Gets set of categories a words belongs to.
     * @return set of categories
     */
    Set<WordCategory> getCategories();

    /**
     * Adds new category to which words belongs to.
     * @param category is category to add.
     */
    void addCategory(WordCategory category);

    /**
     * Removes category from the words.
     * If words has only one category it cannot be removed.
     * @param category is category to remove.
     */
    void removeCategory(WordCategory category);

    /**
     * Gets number of categories a words belongs to.
     * @return number of categories a words belongs to.
     */
    int categoriesCount();
}
