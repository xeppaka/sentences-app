package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.words.CategorizedWord;
import com.xeppaka.sentence.words.Word;
import com.xeppaka.sentence.words.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

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
                new CategorizedWord("table", WordCategory.NOUN),
                new CategorizedWord("green", WordCategory.OBJECTIVE),
                new CategorizedWord("stay", WordCategory.VERB),
                new CategorizedWord("lay", WordCategory.VERB),
                new CategorizedWord("write", WordCategory.VERB),
                new CategorizedWord("hear", WordCategory.VERB),
                new CategorizedWord("ugly", WordCategory.OBJECTIVE),
                new CategorizedWord("pretty", WordCategory.OBJECTIVE),
                new CategorizedWord("high", WordCategory.OBJECTIVE),
                new CategorizedWord("phone", WordCategory.NOUN),
                new CategorizedWord("monitor", WordCategory.NOUN),
                new CategorizedWord("bus", WordCategory.NOUN),
                new CategorizedWord("tiger", WordCategory.NOUN)
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
        Assert.assertEquals(word0, foundWord0);
        final Word foundWord1 = wordsRepository.findWord(word0.getChars().toLowerCase());
        Assert.assertEquals(word0, foundWord1);

        final Random r = new Random(System.currentTimeMillis());
        final char[] chars = word0.getChars().toCharArray();
        for (int i = 0; i < 3; ++i) {
            final int index = Math.abs(r.nextInt(chars.length));
            chars[index] = r.nextInt() % 2 == 0 ? Character.toUpperCase(chars[index]) : Character.toLowerCase(chars[index]);
        }

        final Word randomCaseWord = wordsRepository.findWord(String.valueOf(chars));
        Assert.assertEquals(word0, randomCaseWord);
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
