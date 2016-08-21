package com.xeppaka.sentence.sentences;

import com.xeppaka.sentence.domain.sentences.Sentence;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.service.SentencesService;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Class represents REST controller for sentences.
 */
@RestController
@RequestMapping("sentences")
public class SentencesController {
    private static final Logger log = LoggerFactory.getLogger(SentencesController.class);
    @Autowired
    private SentencesService sentencesService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SentencesDto> findAllSentences() {
        final List<Sentence> sentences = sentencesService.findAllSentencesToShow();
        return new ResponseEntity<>(new SentencesDto(sentences.stream().map(this::createDtoForSentence).collect(Collectors.toList())), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{sentenceId}")
    public ResponseEntity<?> getSentence(@PathVariable long sentenceId) {
        final Sentence sentence = sentencesService.findSentenceToShow(sentenceId);

        if (sentence == null) {
            log.debug("Sentence with id {} is not found.", sentenceId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(createDtoForSentence(sentence), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "generate")
    public ResponseEntity<SentenceDto> generateHumanSentence() {
        try {
            final Sentence sentence = sentencesService.generateHumanSentenceToShow();
            return new ResponseEntity<>(createDtoForSentence(sentence), HttpStatus.OK);
        } catch (NotEnoughWordsException e) {
            log.error("Not enough words in the system to generate new word.", e);
            return new ResponseEntity<>(HttpStatus.PRECONDITION_FAILED);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "{sentenceId}/yodaTalk")
    public ResponseEntity<?> getYodaSentence(@PathVariable long sentenceId) {
        try {
            return new ResponseEntity<>(new YodaSentenceDto(sentencesService.getYodaSentence(sentenceId)), HttpStatus.OK);
        } catch (SentenceNotFoundException e) {
            log.error(MessageFormat.format("Sentence with id {0} is not found.", sentenceId), e);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private SentenceDto createDtoForSentence(Sentence sentence) {
        final Set<Long> sameTextIds = sentencesService.findSentencesWithSameText(sentence.getId());
        return new SentenceDto(sentence, sameTextIds);
    }
}
