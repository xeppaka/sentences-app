package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                new CategorizedWord("table", WordCategory.NOUN, WordCategory.ADJECTIVE),
                new CategorizedWord("green", WordCategory.ADJECTIVE),
                new CategorizedWord("stay", WordCategory.VERB, WordCategory.NOUN, WordCategory.ADJECTIVE),
                new CategorizedWord("lay", WordCategory.VERB, WordCategory.ADJECTIVE),
                new CategorizedWord("write", WordCategory.VERB),
                new CategorizedWord("hear", WordCategory.VERB, WordCategory.ADJECTIVE),
                new CategorizedWord("ugly", WordCategory.ADJECTIVE),
                new CategorizedWord("pretty", WordCategory.ADJECTIVE),
                new CategorizedWord("high", WordCategory.ADJECTIVE, WordCategory.VERB, WordCategory.NOUN),
                new CategorizedWord("phone", WordCategory.NOUN, WordCategory.VERB),
                new CategorizedWord("monitor", WordCategory.NOUN, WordCategory.VERB),
                new CategorizedWord("bus", WordCategory.NOUN, WordCategory.VERB, WordCategory.ADJECTIVE),
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
        wordForUpdating.addCategory(WordCategory.ADJECTIVE);

        wordsRepository.save(word);
        Assert.assertTrue(word.hasId());
        wordsRepository.save(wordForUpdating);
        final Word foundWord = wordsRepository.findWord("eubicor");
        Assert.assertEquals(2, foundWord.categoriesCount());
        Assert.assertTrue(foundWord.getCategories().containsAll(Arrays.asList(WordCategory.VERB, WordCategory.ADJECTIVE)));
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
    public void testDeleteWordByValueRemovesEntityFromRepository() {
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

    @Test
    public void testFindRandomWordsReturnNullsForEmptyRepository() {
        for (WordCategory wordCategory : WordCategory.values()) {
            Assert.assertNull(wordsRepository.findRandomWordForCategory(wordCategory));
        }
    }

    @Test
    public void testFindRandomWordsReturnWordAtLeastOnes() {
        wordsRepository.save(Arrays.asList(words));
        final int N = 300;
        final List<Word> randomResult = new ArrayList<>(N);

        for (int i = 0; i < N; ++i) {
            for (WordCategory wordCategory : WordCategory.values()) {
                randomResult.add(wordsRepository.findRandomWordForCategory(wordCategory));
            }
        }

        for (Word word : words) {
            Assert.assertTrue(randomResult.contains(word));
        }
    }

    @Test
    public void testFindRandomWordsNotReturningDeletedWord() {
        wordsRepository.save(Arrays.asList(words));
        final int N = 300;
        final List<Word> randomResults = new ArrayList<>(N);

        for (int i = 0; i < N; ++i) {
            for (WordCategory wordCategory : WordCategory.values()) {
                randomResults.add(wordsRepository.findRandomWordForCategory(wordCategory));
            }
        }

        Assert.assertTrue(randomResults.contains(words[0]));
        wordsRepository.delete(words[0].getId());

        final List<Word> afterDeleteRandomResults = new ArrayList<>(N);

        for (int i = 0; i < N; ++i) {
            for (WordCategory wordCategory : WordCategory.values()) {
                afterDeleteRandomResults.add(wordsRepository.findRandomWordForCategory(wordCategory));
            }
        }

        Assert.assertFalse(afterDeleteRandomResults.contains(words[0]));
    }

    @Test
    public void testFindRandomWordReturnsNullIfAllWordsAreDeleted() {
        wordsRepository.save(Arrays.asList(words));

        for (Word word : words) {
            wordsRepository.delete(word.getId());
        }

        for (WordCategory wordCategory : WordCategory.values()) {
            Assert.assertNull(wordsRepository.findRandomWordForCategory(wordCategory));
        }
    }

    @Test
    public void testFindRandomWordWithOnlyOneWordInRepository() {
        wordsRepository.save(words[0]);

        for (WordCategory wordCategory : words[0].getCategories()) {
            Assert.assertEquals(words[0], wordsRepository.findRandomWordForCategory(wordCategory));
        }
    }
}
