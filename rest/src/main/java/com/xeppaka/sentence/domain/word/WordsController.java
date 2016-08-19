package com.xeppaka.sentence.domain.word;

import com.xeppaka.sentence.domain.word.Word.WordCategory;
import com.xeppaka.sentence.service.exceptions.WordNotFoundException;
import com.xeppaka.sentence.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("words")
public class WordsController {
    @Autowired
    private WordsService wordsService;

    @RequestMapping(method = RequestMethod.GET)
    public List<WordDto> findAllWords() {
        final List<Word> words = wordsService.findAllWords();

        return words.stream().map(WordDto::new).collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{word}")
    public ResponseEntity<WordDto> saveWord(@PathVariable String word, @RequestBody WordDto wordDto) throws URISyntaxException {
        final WordCategory[] categories = new WordCategory[wordDto.getWordCategories().size()];
        wordDto.getWordCategories().toArray(categories);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(MessageFormat.format("words/{0}", word)));

        return new ResponseEntity<>(new WordDto(wordsService.saveWord(word, categories)), headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{word}")
    public ResponseEntity<?> findWord(@PathVariable String word) {
        try {
            return new ResponseEntity<>(new WordDto(wordsService.findWord(word)), HttpStatus.OK);
        } catch (WordNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
