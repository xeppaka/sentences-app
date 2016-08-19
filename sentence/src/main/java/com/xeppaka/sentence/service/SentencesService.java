package com.xeppaka.sentence.service;

import com.xeppaka.sentence.domain.sentence.HumanSentenceGenerator;
import com.xeppaka.sentence.domain.sentence.Sentence;
import com.xeppaka.sentence.domain.sentence.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.persistence.SentencesRepository;
import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class SentencesService {
    @Autowired
    private SentencesRepository sentencesRepository;
    @Autowired
    private WordsRepository wordsRepository;

    private final HumanSentenceGenerator humanSentenceGenerator;

    {
        humanSentenceGenerator = new HumanSentenceGenerator((category) -> wordsRepository.findRandomWordForCategory(category));
    }

    public List<Sentence> findAllSentences() {
        return sentencesRepository.findAll();
    }

    public Sentence generateHumanSentence() throws NotEnoughWordsException {
        return humanSentenceGenerator.generate();
    }

    public Sentence findSentence(long sentenceId) throws SentenceNotFoundException {
        final Sentence sentence = sentencesRepository.findOne(sentenceId);

        if (sentence == null) {
            throw new SentenceNotFoundException(sentenceId);
        }

        return sentence;
    }

    public Sentence getYodaSentence(long humanSentenceId) {
        return sentencesRepository.findOne(humanSentenceId).toYodaSentence();
    }
}
