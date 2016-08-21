package com.xeppaka.sentence.sentences;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.sentences.Sentence;

/**
 *
 */
@JsonRootName("sentence")
public class YodaSentenceDto {
    private String text;
    private int showDisplayCount;

    private YodaSentenceDto() { }

    public YodaSentenceDto(String text, int showDisplayCount) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null.");
        }

        if (showDisplayCount < 0) {
            throw new IllegalArgumentException("showDisplayCount must not be negative.");
        }

        this.text = text;
        this.showDisplayCount = showDisplayCount;
    }

    public YodaSentenceDto(Sentence sentence) {
        this(sentence.getText(), sentence.getViewCount());
    }

    public String getText() {
        return text;
    }

    public int getShowDisplayCount() {
        return showDisplayCount;
    }
}
