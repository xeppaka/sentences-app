package com.xeppaka.sentence.sentences;

import com.xeppaka.sentence.domain.sentences.Sentence;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.service.SentencesService;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("sentences")
public class SentencesController {
    @Autowired
    private SentencesService sentencesService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<SentencesDto> findAllSentences() {
        final List<Sentence> sentences = sentencesService.findAllSentencesToShow();
        return new ResponseEntity<>(new SentencesDto(sentences.stream().map(SentenceDto::new).collect(Collectors.toList())), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{sentenceId}")
    public ResponseEntity<?> getSentence(@PathVariable long sentenceId) {
        final Sentence sentence = sentencesService.findSentenceToShow(sentenceId);

        if (sentence == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new SentenceDto(sentence), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "generate")
    public ResponseEntity<SentenceDto> generateHumanSentence() throws NotEnoughWordsException {
        final Sentence humanSentence = sentencesService.generateHumanSentenceToShow();
        return new ResponseEntity<>(new SentenceDto(humanSentence), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{sentenceId}/yodaTalk")
    public ResponseEntity<?> getYodaSentence(@PathVariable long sentenceId) {
        try {
            return new ResponseEntity<>(new YodaSentenceDto(sentencesService.getYodaSentence(sentenceId)), HttpStatus.OK);
        } catch (SentenceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
