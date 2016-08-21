package com.xeppaka.sentence.service;

import com.xeppaka.sentence.SentenceConfiguration;
import com.xeppaka.sentence.domain.sentences.Sentence;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SentenceConfiguration.class)
public class SentencesServiceTest {
    @Autowired
    private SentencesService sentencesService;
    @Autowired
    private WordsService wordsService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGenerateSentence() throws NotEnoughWordsException {
        final Word stayWord = wordsService.saveWord("stay", WordCategory.VERB);
        final Word straightWord = wordsService.saveWord("straight", WordCategory.ADJECTIVE);
        final Word humanWord = wordsService.saveWord("human", WordCategory.NOUN);

        final Sentence sentence = sentencesService.generateHumanSentenceToShow();

        assertThat(sentence.getWord(0), equalTo(humanWord));
        assertThat(sentence.getWord(1), equalTo(stayWord));
        assertThat(sentence.getWord(2), equalTo(straightWord));
    }

    @Test(expected = NotEnoughWordsException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testGenerateThrowsExceptionIfNotEnoughWords() throws NotEnoughWordsException {
        sentencesService.generateHumanSentenceToShow();
    }

    @Test
    public void testFindSentence() throws NotEnoughWordsException {
        wordsService.saveWord("stay", WordCategory.VERB);
        wordsService.saveWord("straight", WordCategory.ADJECTIVE);
        wordsService.saveWord("human", WordCategory.NOUN);

        final Sentence generatedSentence = sentencesService.generateHumanSentenceToShow();
        final Sentence foundSentence = sentencesService.findSentence(generatedSentence.getId());

        assertThat(foundSentence, equalTo(generatedSentence));
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testFindSentenceReturnNullForUnknownId() {
        final Sentence sentence = sentencesService.findSentence(0l);
        assertThat(sentence, nullValue());
    }

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testYodaSentenceCorrectlyGenerated() throws NotEnoughWordsException, SentenceNotFoundException {
        final Word stayWord = wordsService.saveWord("stay", WordCategory.VERB);
        final Word straightWord = wordsService.saveWord("straight", WordCategory.ADJECTIVE);
        final Word humanWord = wordsService.saveWord("human", WordCategory.NOUN);

        final Sentence sentence = sentencesService.generateHumanSentenceToShow();
        final Sentence yodaSentence = sentencesService.getYodaSentence(sentence.getId());

        assertThat(yodaSentence.getWord(0), equalTo(humanWord));
        assertThat(yodaSentence.getWord(1), equalTo(straightWord));
        assertThat(yodaSentence.getWord(2), equalTo(stayWord));
    }

    @Test(expected = SentenceNotFoundException.class)
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
    public void testYodaThrowsExceptionIfSentenceIdIsUnknown() throws SentenceNotFoundException {
        sentencesService.getYodaSentence(0L);
    }
}
