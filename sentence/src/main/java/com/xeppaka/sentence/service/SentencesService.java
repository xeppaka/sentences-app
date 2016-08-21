package com.xeppaka.sentence.service;

import com.xeppaka.sentence.domain.sentences.HumanSentenceGenerator;
import com.xeppaka.sentence.domain.sentences.Sentence;
import com.xeppaka.sentence.domain.sentences.exceptions.NotEnoughWordsException;
import com.xeppaka.sentence.persistence.SentencesRepository;
import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.service.exceptions.SentenceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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

    public List<Sentence> findAllSentencesToShow() {
        final List<Sentence> sentences = sentencesRepository.findAll();

        for (Sentence sentence : sentences) {
            sentence.increaseViewCount();
        }

        return sentences;
    }

    public Sentence generateHumanSentenceToShow() throws NotEnoughWordsException {
        final Sentence sentence = humanSentenceGenerator.generate();
        sentence.increaseViewCount();
        return sentencesRepository.save(sentence);
    }

    public Sentence findSentence(long sentenceId) {
        return sentencesRepository.findOne(sentenceId);
    }

    public Set<Long> findSentencesWithSameText(long sentenceId) {
        return sentencesRepository.findSentencesWithSameText(sentenceId);
    }

    public Sentence findSentenceToShow(long sentenceId) {
        final Sentence sentence = sentencesRepository.findOne(sentenceId);

        if (sentence != null) {
            sentence.increaseViewCount();
        }

        return sentence;
    }

    public Sentence getYodaSentence(long sentenceId) throws SentenceNotFoundException {
        final Sentence humanSentence = findSentence(sentenceId);

        if (humanSentence == null) {
            throw new SentenceNotFoundException(sentenceId);
        }

        return humanSentence.toYodaSentence();
    }
}
