package com.xeppaka.sentence.words;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

/**
 * Class represents DTO for Word.
 */
@JsonRootName("word")
public class WordDto {
    private String word;
    private Set<WordCategory> wordCategories;

    private WordDto() { }

    public WordDto(String word, Collection<WordCategory> wordCategories) {
        validate(word, wordCategories);

        this.word = word;
        this.wordCategories = EnumSet.copyOf(wordCategories);
    }

    public WordDto(Word word) {
        this(word.getChars(), word.getCategories());
    }

    public String getWord() {
        return word;
    }

    public Set<WordCategory> getWordCategories() {
        return wordCategories;
    }

    private void validate(String word, Collection<WordCategory> wordCategory) {
        if (word == null || word.isEmpty()) {
            throw new IllegalArgumentException("words must not be null or empty.");
        }

        if (wordCategory == null) {
            throw new IllegalArgumentException("wordCategories must not be null.");
        }
    }
}
