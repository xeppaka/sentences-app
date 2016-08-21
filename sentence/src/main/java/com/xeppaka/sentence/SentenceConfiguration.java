package com.xeppaka.sentence;

import com.xeppaka.sentence.persistence.InMemorySentencesRepository;
import com.xeppaka.sentence.persistence.InMemoryWordsRepository;
import com.xeppaka.sentence.persistence.SentencesRepository;
import com.xeppaka.sentence.persistence.WordsRepository;
import com.xeppaka.sentence.service.SentencesService;
import com.xeppaka.sentence.service.WordsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Class represents spring configuration of sentence module.
 */
@Configuration
public class SentenceConfiguration {
    @Bean
    public SentencesService sentencesService() {
        return new SentencesService();
    }

    @Bean
    public WordsService wordsService() {
        return new WordsService();
    }

    @Bean
    public SentencesRepository sentencesRepository() {
        return new InMemorySentencesRepository();
    }

    @Bean
    public WordsRepository wordsRepository() {
        return new InMemoryWordsRepository();
    }
}
