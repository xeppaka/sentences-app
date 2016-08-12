package com.xeppaka.sentence.words;

import com.xeppaka.sentence.Entity;

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
    WordCategory getCategory();
}
