package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Represents words repository with typical functions for repository - find/save/remove, etc.
 */
public interface WordsRepository extends CrudRepository<Word, Long> {
    @Override
    List<Word> findAll();
    Word findWord(String chars);
    Word findRandomWordForCategory(WordCategory wordCategory);
}
