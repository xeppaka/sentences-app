package com.xeppaka.sentence;

import com.xeppaka.sentence.words.WordsController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {RestConfiguration.class, SentenceConfiguration.class})
public class WordsControllerTest {
    @Autowired
    private WordsController wordsController;

    @Test
    public void testSmth() {
        System.out.println(wordsController.findAllWords());
    }
}
