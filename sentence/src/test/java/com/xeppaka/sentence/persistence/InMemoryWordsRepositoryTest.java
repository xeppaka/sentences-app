package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
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
    public void testSaveAndFindWork() {
        Assert.assertFalse(words[0].hasId());
        final Word word0 = wordsRepository.save(words[0]);
        Assert.assertTrue(words[0].hasId());

        final Word foundWordByChars = wordsRepository.findWord(word0.getChars());
        Assert.assertEquals(word0, foundWordByChars);
        final Word foundWordById = wordsRepository.findOne(word0.getId());
        Assert.assertEquals(word0, foundWordById);
        Assert.assertEquals(1, wordsRepository.count());
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
        Assert.assertEquals(1, wordsRepository.count());
    }

    @Test
    public void testCharsCaseIsIgnoredWhenSaveAndFindWord() {
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
    public void testSaveMultipleEntities() {
        Iterable<Word> savedWords = wordsRepository.save(Arrays.asList(words));
        for (Word savedWord : savedWords) {
            Assert.assertTrue(savedWord.hasId());
        }

        Assert.assertEquals(words.length, wordsRepository.count());

        for (Word word : words) {
            Assert.assertEquals(word, wordsRepository.findWord(word.getChars()));
        }
    }

    @Test
    public void testWordIsUpdated() {
        final Word word = new CategorizedWord("Eubicor", WordCategory.VERB);
        final Word wordForUpdating = new CategorizedWord(word);
        wordForUpdating.addCategory(WordCategory.OBJECTIVE);

        wordsRepository.save(word);
        Assert.assertTrue(word.hasId());
        wordsRepository.save(wordForUpdating);
        final Word foundWord = wordsRepository.findWord("eubicor");
        Assert.assertEquals(2, foundWord.categoriesCount());
        Assert.assertTrue(foundWord.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.OBJECTIVE)));
    }

    @Test
    public void testFindOneById() {
        final Iterable<Word> savedWords = wordsRepository.save(Arrays.asList(words));
        for (Word savedWord : savedWords) {
            Assert.assertTrue(savedWord.hasId());
            Assert.assertEquals(savedWord, wordsRepository.findOne(savedWord.getId()));
        }
    }

    @Test
    public void testFindOneEmptyResult() {
        Assert.assertNull(wordsRepository.findOne(0l));
    }

    @Test
    public void testExistsReturnTrueAfterWordSave() {
        final Iterable<Word> savedWords = wordsRepository.save(Arrays.asList(words));
        for (Word savedWord : savedWords) {
            Assert.assertTrue(savedWord.hasId());
            Assert.assertTrue(wordsRepository.exists(savedWord.getId()));
        }
    }

    @Test
    public void testRepositoryCountReturnIncreasedInteger() {
        Assert.assertEquals(0, wordsRepository.count());

        for (int i = 0; i < words.length; ++i) {
            wordsRepository.save(words[i]);
            Assert.assertEquals(i + 1, wordsRepository.count());
        }
    }

    @Test
    public void testDeleteByIdRemovesEntityFromRepository() {
        final Word savedWord1 = wordsRepository.save(words[0]);
        final Word savedWord2 = wordsRepository.save(words[1]);
        final Word savedWord3 = wordsRepository.save(words[2]);

        Assert.assertEquals(3, wordsRepository.count());
        wordsRepository.delete(savedWord1.getId());
        Assert.assertEquals(2, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord1.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord2.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord3.getChars()));
        wordsRepository.delete(savedWord2.getId());
        Assert.assertEquals(1, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord2.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord3.getChars()));
        wordsRepository.delete(savedWord3.getId());
        Assert.assertEquals(0, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord3.getChars()));

        Assert.assertEquals(0, wordsRepository.count());
    }

    @Test
    public void testDeleteWordByValueRemovesEneityFromRepository() {
        final Word savedWord1 = wordsRepository.save(words[0]);
        final Word savedWord2 = wordsRepository.save(words[1]);
        final Word savedWord3 = wordsRepository.save(words[2]);

        Assert.assertEquals(3, wordsRepository.count());
        wordsRepository.delete(savedWord1);
        Assert.assertEquals(2, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord1.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord2.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord3.getChars()));
        wordsRepository.delete(savedWord2);
        Assert.assertEquals(1, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord2.getChars()));
        Assert.assertNotNull(wordsRepository.findWord(savedWord3.getChars()));
        wordsRepository.delete(savedWord3);
        Assert.assertEquals(0, wordsRepository.count());
        Assert.assertNull(wordsRepository.findWord(savedWord3.getChars()));

        Assert.assertEquals(0, wordsRepository.count());
    }

    @Test
    public void testDeleteAllRemovesAllEntitiesFromRepository() {
        final Iterable<Word> savedWords = wordsRepository.save(Arrays.asList(words));

        wordsRepository.deleteAll();
        Assert.assertEquals(0, wordsRepository.count());

        for (Word savedWord : savedWords) {
            Assert.assertNull(wordsRepository.findWord(savedWord.getChars()));
        }
    }
}
