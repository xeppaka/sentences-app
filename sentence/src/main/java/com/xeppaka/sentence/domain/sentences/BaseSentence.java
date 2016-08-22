package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 * Base class for sentences. Tracks number of views and date and time when it was generated.
 */
public abstract class BaseSentence extends BaseEntity implements Sentence {
    private final LocalDateTime generatedOn;
    private int viewCount;

    public BaseSentence() {
        generatedOn = LocalDateTime.now();
    }

    @Override
    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    @Override
    public int getViewCount() {
        return viewCount;
    }

    @Override
    public int increaseViewCount() {
        return ++viewCount;
    }

    @Override
    public String getText() {
        final StringBuilder sb = new StringBuilder();
        final int words = wordCount();

        for (int i = 0; i < words; ++i) {
            if (i > 0) {
                sb.append(' ');
            }

            sb.append(getWord(i).getChars());
        }

        return sb.toString();
    }
}
