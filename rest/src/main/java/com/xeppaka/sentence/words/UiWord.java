package com.xeppaka.sentence.words;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.words.Word.WordCategory;

/**
 *
 */
@JsonRootName(value = "word")
public class UiWord {
    private String word;
    private WordCategory wordCategory;

    public UiWord(String word, WordCategory wordCategory) {
        validate(word, wordCategory);

        this.word = word;
        this.wordCategory = wordCategory;
    }

    public UiWord(Word word) {
        this(word.getChars(), word.getCategory());
    }

    private void validate(String word, WordCategory wordCategory) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("word must not be null or empty.");
        }

        if (wordCategory == null) {
            throw new IllegalArgumentException("wordCategory must not be null.");
        }
    }
}
