package com.xeppaka.sentence.service.exceptions;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 */
public class SentenceNotFoundException extends Exception {
    private final long id;

    public SentenceNotFoundException(long id) {
        super(MessageFormat.format(ResourceBundle.getBundle("com.xeppaka.sentence.messages").getString("sentenceNotFound"), id));
        this.id = id;
    }

    public long getId() {
        return id;
    }
}
