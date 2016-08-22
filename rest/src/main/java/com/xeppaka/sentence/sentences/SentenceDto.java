package com.xeppaka.sentence.sentences;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.xeppaka.sentence.domain.sentences.Sentence;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

/**
 * Class represents DTO for sentence.
 */
@JsonRootName("sentence")
public class SentenceDto {
    private final long id;
    private final String text;
    private final int showDisplayCount;
    private final String generatedOn;
    private final Set<Long> sentencesWithSameText;

    private SentenceDto() {
        this.id = -1;
        text = null;
        showDisplayCount = 0;
        generatedOn = LocalDateTime.now().toString();
        sentencesWithSameText = Collections.emptySet();
    }

    public SentenceDto(long id, String text, int showDisplayCount, LocalDateTime generatedOn, Set<Long> sentencesWithSameText) {
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
        this.generatedOn = generatedOn.toString();
        this.sentencesWithSameText = sentencesWithSameText;
    }

    public SentenceDto(Sentence sentence, Set<Long> sentencesWithSameText) {
        this(sentence.getId(), sentence.getText(), sentence.getViewCount(), sentence.getGeneratedOn(), sentencesWithSameText);
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

    public String getGeneratedOn() {
        return generatedOn;
    }

    public Set<Long> getSentencesWithSameText() {
        return sentencesWithSameText;
    }
}
