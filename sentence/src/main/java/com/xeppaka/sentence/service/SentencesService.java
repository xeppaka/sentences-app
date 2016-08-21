package com.xeppaka.sentence.service;

import com.xeppaka.sentence.domain.sentences.HumanSentenceGenerator;
import com.xeppaka.sentence.domain.sentences.Sentence;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.persistence.SentencesRepository;
import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 *
 */
@Service
public class SentencesService {
    private static final Logger log = LoggerFactory.getLogger(SentencesService.class);

    @Autowired
    private SentencesRepository sentencesRepository;
    @Autowired
    private WordsRepository wordsRepository;

    private final HumanSentenceGenerator humanSentenceGenerator;

    {
        humanSentenceGenerator = new HumanSentenceGenerator((category) -> wordsRepository.findRandomWordForCategory(category));
    }

    public List<Sentence> findAllSentencesToShow() {
        log.trace("Finding all sentences to show on UI.");

        final List<Sentence> sentences = sentencesRepository.findAll();
        for (Sentence sentence : sentences) {
            sentence.increaseViewCount();
        }

        log.trace("Number of returned sentences is: {}.", sentences.size());
        return sentences;
    }

    public Sentence generateHumanSentenceToShow() throws NotEnoughWordsException {
        log.trace("Generating new human form of sentence.");

        final Sentence sentence = humanSentenceGenerator.generate();
        sentence.increaseViewCount();
        final Sentence savedSentence = sentencesRepository.save(sentence);

        log.trace("Generated sentence is: {}.", savedSentence);
        return savedSentence;
    }

    public Sentence findSentence(long sentenceId) {
        log.trace("Finding sentence with id: {}.", sentenceId);

        final Sentence sentence = sentencesRepository.findOne(sentenceId);

        log.trace("Found sentence: {}.", sentence);
        return sentence;
    }

    public Set<Long> findSentencesWithSameText(long sentenceId) {
        log.trace("Finding sentences with same text as sentence with id: {}.", sentenceId);

        final Set<Long> ids = sentencesRepository.findSentencesWithSameText(sentenceId);

        log.trace("Found ids: {}", ids );
        return ids;
    }

    public Sentence findSentenceToShow(long sentenceId) {
        log.trace("Finding sentence to show on UI with id: {}.", sentenceId);

        final Sentence sentence = sentencesRepository.findOne(sentenceId);

        if (sentence != null) {
            sentence.increaseViewCount();
        }

        log.trace("Found sentence: {}.", sentence);
        return sentence;
    }

    public Sentence getYodaSentence(long sentenceId) throws SentenceNotFoundException {
        log.trace("Getting Yoda form of the sentence for id: {}.", sentenceId);

        final Sentence humanSentence = findSentence(sentenceId);

        if (humanSentence == null) {
            throw new SentenceNotFoundException(sentenceId);
        }

        final Sentence yodaSentence = humanSentence.toYodaSentence();
        log.trace("Yoda form of the sentence is: {}.", yodaSentence);

        return yodaSentence;
    }
}
