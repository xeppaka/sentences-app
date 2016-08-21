package com.xeppaka.sentence.sentences;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.sentences.Sentence;

/**
 *
 */
@JsonRootName("sentence")
public class SentenceDto {
    private long id;
    private String text;
    private int showDisplayCount;

    private SentenceDto() { }

    public SentenceDto(long id, String text, int showDisplayCount) {
        if (id < 0) {
            throw new IllegalArgumentException("id must not be negative.");
        }

        if (text == null) {
            throw new IllegalArgumentException("text must not be null.");
        }

        if (showDisplayCount < 0) {
            throw new IllegalArgumentException("showDisplayCount must not be negative.");
        }

        this.id = id;
        this.text = text;
        this.showDisplayCount = showDisplayCount;
    }

    public SentenceDto(Sentence sentence) {
        this(sentence.getId(), sentence.getText(), sentence.getViewCount());
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getShowDisplayCount() {
        return showDisplayCount;
    }
}
