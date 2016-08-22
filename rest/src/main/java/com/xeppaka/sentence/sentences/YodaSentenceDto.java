package com.xeppaka.sentence.sentences;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.sentences.Sentence;

/**
 * Class represents DTO for Yoda form of sentence.
 */
@JsonRootName("sentence")
public class YodaSentenceDto {
    private final String text;

    private YodaSentenceDto() {
        this.text = null;
    }

    public YodaSentenceDto(String text, int showDisplayCount) {
        if (text == null) {
            throw new IllegalArgumentException("text must not be null.");
        }

        if (showDisplayCount < 0) {
            throw new IllegalArgumentException("showDisplayCount must not be negative.");
        }

        this.text = text;
    }

    public YodaSentenceDto(Sentence sentence) {
        this(sentence.getText(), sentence.getViewCount());
    }

    public String getText() {
        return text;
    }
}
