package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.words.Word;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 */
public interface WordsRepository extends CrudRepository<Word, Long> {
    @Override
    List<Word> findAll();
    @Override
    List<Word> findAll(Iterable<Long> longs);
    Word findByChars(String chars);
}
