package com.xeppaka.sentence.words;

import com.xeppaka.sentence.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController("/words")
public class WordsController {
    @Autowired
    private WordsService wordsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<UiWord> getAllWords() {
        final List<Word> words = wordsService.getWords();

        return words.stream().map(UiWord::new).collect(Collectors.toList());
    }
}
