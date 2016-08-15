package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.words.WordInCategory;
import com.xeppaka.sentence.words.Word;
import com.xeppaka.sentence.words.Word.WordCategory;
import org.junit.Assert;
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
        Assert.assertFalse(words[0].hasId());
        final Word word0 = wordsRepository.save(words[0]);
        Assert.assertTrue(words[0].hasId());

        final Word foundWordByChars = wordsRepository.findWord(word0.getChars());
        Assert.assertEquals(word0, foundWordByChars);
        final Word foundWordById = wordsRepository.findOne(word0.getId());
        Assert.assertEquals(word0, foundWordById);
    }

    @Test
    public void testIdNotChangedOnSaveMultipleTimes() {
        Assert.assertFalse(words[0].hasId());
        final Word word0 = wordsRepository.save(words[0]);
        Assert.assertTrue(words[0].hasId());

        final long id = word0.getId();
        wordsRepository.save(word0);
        Assert.assertEquals(id, word0.getId());
        wordsRepository.save(word0);
        Assert.assertEquals(id, word0.getId());
    }

    @Test
    public void testCharsCaseIsIgnored() {
        final Word word0 = wordsRepository.save(words[0]);
        final Word foundWord0 = wordsRepository.findWord(word0.getChars().toUpperCase());
        final Word foundWord1 = wordsRepository.findWord(word0.getChars().toLowerCase());

    }

    @Test
    public void testWordIsUpdatedInRepository() {
        final Word word0 = wordsRepository.save(words[0]);
        Assert.assertTrue(word0.getId() >= 0);
        final Word foundWordByChars = wordsRepository.findWord(word0.getChars());
        Assert.assertEquals(word0, foundWordByChars);
        final Word foundWordById = wordsRepository.findOne(word0.getId());
        Assert.assertEquals(word0, foundWordById);

    }
}
