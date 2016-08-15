package com.xeppaka.sentence.service;

import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.words.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class WordsService {
    @Autowired
    private WordsRepository wordsRepository;

    public List<Word> getWords() {
        return wordsRepository.findAll();
    }

    public Word getWord(String chars) throws WordNotFoundException {
        final Word result = wordsRepository.findWord(chars);

        if (result == null) {
            throw new WordNotFoundException();
        }

        return result;
    }

    public Word saveWord(Word word) {
        return wordsRepository.save(word);
    }
}
