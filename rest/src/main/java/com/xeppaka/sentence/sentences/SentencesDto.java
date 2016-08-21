package com.xeppaka.sentence.sentences;

import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.List;

/**
 *
 */
@JsonRootName("sentences")
public class SentencesDto {
    private List<SentenceDto> sentenceList;
    private int sentencesCount;

    public SentencesDto(List<SentenceDto> sentenceList) {
        this.sentenceList = sentenceList;
        this.sentencesCount = sentenceList.size();
    }

    public List<SentenceDto> getSentenceList() {
        return sentenceList;
    }

    public int getSentencesCount() {
        return sentencesCount;
    }
}
