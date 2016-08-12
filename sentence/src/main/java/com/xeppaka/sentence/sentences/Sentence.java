package com.xeppaka.sentence.sentences;

import com.xeppaka.sentence.words.Word;

/**
 *
 */
public interface Sentence {
    Word getWord(int index);
    int wordCount();
}
