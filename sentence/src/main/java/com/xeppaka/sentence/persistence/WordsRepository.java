package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.words.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 */
public interface WordsRepository extends CrudRepository<Word, Long> {
    @Override
    List<Word> findAll();
    Word findWord(String chars);
}
