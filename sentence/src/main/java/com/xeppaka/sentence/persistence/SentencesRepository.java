package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.sentence.Sentence;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 *
 */
public interface SentencesRepository extends CrudRepository<Sentence, Long> {
    @Override
    List<Sentence> findAll();
}
