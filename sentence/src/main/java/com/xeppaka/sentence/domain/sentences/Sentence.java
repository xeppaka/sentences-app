package com.xeppaka.sentence.domain.sentences;

import com.xeppaka.sentence.domain.words.Word;

/**
 *
 */
public interface Sentence {
    Word getWord(int index);
    int wordCount();
}
