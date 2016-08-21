package com.xeppaka.sentence.service;

import com.xeppaka.sentence.domain.words.CategorizedWord;
import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import com.xeppaka.sentence.persistence.WordsRepository;
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

    public Word findWord(String chars) {
        return wordsRepository.findWord(chars);
    }

    public Word saveWord(String chars, WordCategory... categories) {
        return wordsRepository.save(new CategorizedWord(chars, categories));
    }
}
