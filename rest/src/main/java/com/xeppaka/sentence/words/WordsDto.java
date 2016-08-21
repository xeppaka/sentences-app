package com.xeppaka.sentence.words;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 * Class represents DTO for list of words.
 */
@JsonRootName("words")
public class WordsDto {
    private List<WordDto> wordsList;
    private int wordsCount;

    public WordsDto(List<WordDto> wordsList) {
        this.wordsList = wordsList;
        this.wordsCount = wordsList.size();
    }

    public List<WordDto> getWordsList() {
        return wordsList;
    }

    public int getWordsCount() {
        return wordsCount;
    }
}
