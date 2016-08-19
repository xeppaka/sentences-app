package com.xeppaka.sentence.domain.sentence;

import com.xeppaka.sentence.domain.BaseEntity;

import java.time.LocalDateTime;

/**
 *
 */
public abstract class BaseSentence extends BaseEntity implements Sentence {
    private LocalDateTime generatedOn;
    private int viewCount;

    public BaseSentence() {
        generatedOn = LocalDateTime.now();
    }

    public LocalDateTime getGeneratedOn() {
        return generatedOn;
    }

    public int getViewCount() {
        return viewCount;
    }

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
