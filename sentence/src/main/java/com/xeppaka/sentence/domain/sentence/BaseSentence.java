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
}
