package com.xeppaka.sentence.service;

import com.xeppaka.sentence.domain.word.CategorizedWord;
import com.xeppaka.sentence.domain.word.Word;
import com.xeppaka.sentence.domain.word.Word.WordCategory;
import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.service.exceptions.WordNotFoundException;
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

    public List<Word> findAllWords() {
        return wordsRepository.findAll();
    }

    public Word findWord(String chars) throws WordNotFoundException {
        final Word result = wordsRepository.findWord(chars);

        if (result == null) {
            throw new WordNotFoundException(chars);
        }

        return result;
    }

    public Word saveWord(String chars, WordCategory... categories) {
        return wordsRepository.save(new CategorizedWord(chars, categories));
    }
}
