package com.xeppaka.sentence.service;

import com.xeppaka.sentence.SentenceConfiguration;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

/**
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SentenceConfiguration.class)
public class WordsServiceTest {
    @Autowired
    private WordsService wordsService;

    @Test
    public void testSavingWord() {
        final Word word = wordsService.saveWord("Eubicor", WordCategory.NOUN);

        assertThat(word.hasId(), is(true));
        assertThat(word.getChars(), equalTo("Eubicor"));
        assertThat(word.categoriesCount(), equalTo(1));
        assertThat(word.getCategories(), hasItems(WordCategory.NOUN));

        final Word redmi2 = wordsService.saveWord("Redmi2", WordCategory.VERB, WordCategory.ADJECTIVE);

        assertThat(redmi2.hasId(), is(true));
        assertThat(redmi2.getChars(), equalTo("Redmi2"));
        assertThat(redmi2.categoriesCount(), equalTo(2));
        assertThat(redmi2.getCategories(), hasItems(WordCategory.VERB, WordCategory.ADJECTIVE));
    }

    @Test
    public void testFindWord() {
        wordsService.saveWord("Eubicor", WordCategory.NOUN, WordCategory.ADJECTIVE);
        final Word word = wordsService.findWord("eubicor");

        assertThat(word, notNullValue());
        assertThat(word.getChars(), equalTo("Eubicor"));
        assertThat(word.categoriesCount(), equalTo(2));
        assertThat(word.getCategories(), hasItems(WordCategory.NOUN, WordCategory.ADJECTIVE));
    }

    @Test
    public void testSaveReplacesPrevious() {
        wordsService.saveWord("Eubicor", WordCategory.NOUN, WordCategory.ADJECTIVE);
        final Word eubicor = wordsService.findWord("eubicor");

        assertThat(eubicor.getChars(), equalTo("Eubicor"));
        assertThat(eubicor.categoriesCount(), equalTo(2));
        assertThat(eubicor.getCategories(), hasItems(WordCategory.NOUN, WordCategory.ADJECTIVE));

        wordsService.saveWord("Eubicor", WordCategory.NOUN, WordCategory.ADJECTIVE, WordCategory.VERB);
        final Word eubicor2 = wordsService.findWord("eubicor");

        assertThat(eubicor2, not(equalTo(eubicor)));
        assertThat(eubicor2.getChars(), equalTo("Eubicor"));
        assertThat(eubicor2.categoriesCount(), equalTo(3));
        assertThat(eubicor2.getCategories(), hasItems(WordCategory.NOUN, WordCategory.ADJECTIVE, WordCategory.VERB));
    }

    @Test
    public void testFindIfMoreWordsSaved() {
        wordsService.saveWord("Xiaomi", WordCategory.NOUN, WordCategory.ADJECTIVE);
        final Word xiaomiWord = wordsService.findWord("xiAoMi");

        assertThat(xiaomiWord, notNullValue());
        assertThat(xiaomiWord.hasId(), is(true));
        assertThat(xiaomiWord.getChars(), equalTo("Xiaomi"));
        assertThat(xiaomiWord.categoriesCount(), equalTo(2));
        assertThat(xiaomiWord.getCategories(), hasItems(WordCategory.NOUN, WordCategory.ADJECTIVE));

        wordsService.saveWord("Green", WordCategory.VERB, WordCategory.NOUN);
        final Word greenWord = wordsService.findWord("green");

        assertThat(greenWord, not(equalTo(xiaomiWord)));
        assertThat(greenWord, notNullValue());
        assertThat(greenWord.hasId(), is(true));
        assertThat(greenWord.getChars(), equalTo("Green"));
        assertThat(greenWord.categoriesCount(), equalTo(2));
        assertThat(greenWord.getCategories(), hasItems(WordCategory.NOUN, WordCategory.VERB));

        final Word xiaomiWord2 = wordsService.findWord("XiaOmi");
        assertThat(xiaomiWord, equalTo(xiaomiWord2));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testFindAllWords() {
        final Word xiaomiWord = wordsService.saveWord("Xiaomi", WordCategory.NOUN, WordCategory.ADJECTIVE);
        final Word eubicorWord = wordsService.saveWord("Eubicor", WordCategory.VERB);
        final Word glassWord = wordsService.saveWord("Glass", WordCategory.VERB, WordCategory.NOUN);
        final Word cervenyWord = wordsService.saveWord("Cerveny", WordCategory.ADJECTIVE);

        final List<Word> words = wordsService.findAllWords();
        assertThat(words.size(), equalTo(4));
        assertThat(words, hasItems(xiaomiWord, eubicorWord, glassWord, cervenyWord));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveWordWithoutCategoryNotAllowed() {
        wordsService.saveWord("Key");
    }
}
