package com.xeppaka.sentence.domain.word;

import com.xeppaka.sentence.domain.sentence.Sentence;
import com.xeppaka.sentence.domain.sentence.exceptions.NotEnoughWordsException;
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
    public List<SentenceDto> findAllSentences() {
        final List<Sentence> sentences = sentencesService.findAllSentences();

        return sentences.stream().map(SentenceDto::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, value = "{sentenceId}")
    public ResponseEntity<?> getSentence(@PathVariable long sentenceId) {
        try {
            return new ResponseEntity<>(sentencesService.findSentence(sentenceId), HttpStatus.OK);
        } catch (SentenceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "generate")
    public SentenceDto generateHumanSentence() throws NotEnoughWordsException {
        final Sentence humanSentence = sentencesService.generateHumanSentence();
        return new SentenceDto(humanSentence);
    }

    @RequestMapping(method = RequestMethod.POST, value = "{sentenceId}/yodaTalk")
    public SentenceDto getYodaSentence(@PathVariable long sentenceId) {
        return new SentenceDto(sentencesService.getYodaSentence(sentenceId));
    }
}
