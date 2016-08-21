package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface represents  repository of words.
 */
public interface WordsRepository extends CrudRepository<Word, Long> {
    @Override
    List<Word> findAll();

    /**
     * Finds word by provided chars as key.
     *
     * @param chars is sequence of chars as key to search the word.
     * @return word if exists, otherwise null.
     */
    Word findWord(String chars);

    /**
     * Finds random word of specific category.
     *
     * @param wordCategory is category that returned word should have.
     * @return word of specific category. If no such word exist - null.
     */
    Word findRandomWordForCategory(WordCategory wordCategory);
}
