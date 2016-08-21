package com.xeppaka.sentence.words;

import com.xeppaka.sentence.domain.words.Word;
import com.xeppaka.sentence.domain.words.Word.WordCategory;
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
import java.util.ResourceBundle;
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
    public ResponseEntity<WordsDto> findAllWords() {
        final List<Word> words = wordsService.findAllWords();
        final List<WordDto> wordDtos = words.stream().map(WordDto::new).collect(Collectors.toList());

        return new ResponseEntity<>(new WordsDto(wordDtos), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{chars}")
    public ResponseEntity<WordDto> saveWord(@PathVariable String chars, @RequestBody WordDto wordDto) throws URISyntaxException {
        final WordCategory[] categories = new WordCategory[wordDto.getWordCategories().size()];
        wordDto.getWordCategories().toArray(categories);

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI(MessageFormat.format("words/{0}", chars)));

        return new ResponseEntity<>(new WordDto(wordsService.saveWord(chars, categories)), headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "{chars}")
    public ResponseEntity<?> findWord(@PathVariable String chars) {
        final Word word = wordsService.findWord(chars);

        if (word != null) {
            return new ResponseEntity<>(new WordDto(word), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(MessageFormat.format(ResourceBundle.getBundle("com.xeppaka.sentence.messages").getString("wordNotFound"), chars), HttpStatus.NOT_FOUND);
        }
    }
}
