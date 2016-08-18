package com.xeppaka.sentence.service;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 */
public class WordNotFoundException extends Exception {
    private final String wordChars;

    public WordNotFoundException(String wordChars) {
        super(MessageFormat.format(ResourceBundle.getBundle("com.xeppaka.sentence.messages").getString("wordNotFound"), wordChars));
        this.wordChars = wordChars;
    }

    public WordNotFoundException(String s, String wordChars) {
        super(s);
        this.wordChars = wordChars;
    }

    public String getWordChars() {
        return wordChars;
    }
}
