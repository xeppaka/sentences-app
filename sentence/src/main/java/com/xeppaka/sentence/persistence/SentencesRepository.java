package com.xeppaka.sentence.persistence;

import com.xeppaka.sentence.domain.sentences.Sentence;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

/**
 * Interface represents repository of sentences.
 */
public interface SentencesRepository extends CrudRepository<Sentence, Long> {
    @Override
    List<Sentence> findAll();

    /**
     * Finds all sentences with the same text.
     *
     * @param id is of the sentence for which same sentences should be returned.
     * @return set of ids of the sentences with same text as provided in argument. Provided is
     *         is excluded from the result.
     */
    Set<Long> findSentencesWithSameText(Long id);
}
