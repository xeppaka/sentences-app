package com.xeppaka.sentence.domain.words;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 *
 */
@JsonRootName(value = "word")
public class WordDto {
    private String word;
    private Set<WordCategory> wordCategory;

    public WordDto(String word, Collection<WordCategory> wordCategory) {
        validate(word, wordCategory);

        this.word = word;
        this.wordCategory = EnumSet.copyOf(wordCategory);
    }

    public WordDto(Word word) {
        this(word.getChars(), word.getCategories());
    }

    public String getWord() {
        return word;
    }

    public Set<WordCategory> getWordCategory() {
        return wordCategory;
    }

    private void validate(String word, Collection<WordCategory> wordCategory) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("word must not be null or empty.");
        }

        if (wordCategory == null) {
            throw new IllegalArgumentException("wordCategory must not be null.");
        }
    }
}
