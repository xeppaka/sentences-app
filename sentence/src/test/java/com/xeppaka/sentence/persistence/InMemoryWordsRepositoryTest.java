package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.words.WordInCategory;
import com.xeppaka.sentence.words.Word;
import com.xeppaka.sentence.words.Word.WordCategory;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class InMemoryWordsRepositoryTest {
    private WordsRepository wordsRepository;
    private Word[] words;

    @Before
    public void setUp() {
        wordsRepository = new InMemoryWordsRepository();
        words = new Word[]{
                new WordInCategory("table", WordCategory.NOUN),
                new WordInCategory("green", WordCategory.OBJECTIVE),
                new WordInCategory("stay", WordCategory.VERB),
                new WordInCategory("lay", WordCategory.VERB),
                new WordInCategory("write", WordCategory.VERB),
                new WordInCategory("hear", WordCategory.VERB),
                new WordInCategory("ugly", WordCategory.OBJECTIVE),
                new WordInCategory("pretty", WordCategory.OBJECTIVE),
                new WordInCategory("high", WordCategory.OBJECTIVE),
                new WordInCategory("phone", WordCategory.NOUN),
                new WordInCategory("monitor", WordCategory.NOUN),
                new WordInCategory("bus", WordCategory.NOUN),
                new WordInCategory("tiger", WordCategory.NOUN)
        };
    }

    @Test
    public void testWordIsSaved() {
        final Word word0 = wordsRepository.save(words[0]);

    }
}
