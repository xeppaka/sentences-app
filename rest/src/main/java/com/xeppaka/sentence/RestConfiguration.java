package com.xeppaka.sentence;

import com.xeppaka.sentence.sentences.SentencesController;
import com.xeppaka.sentence.words.WordsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration of REST api.
 */
@Configuration
public class RestConfiguration {
    @Bean
    public SentencesController sentencesController() {
        return new SentencesController();
    }

    @Bean
    public WordsController wordsController() {
        return new WordsController();
    }
}
