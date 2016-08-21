package com.xeppaka.sentence.domain.sentences.exceptions;

import java.util.ResourceBundle;

/**
 *
 */
public class NotEnoughWordsException extends Exception {
    public NotEnoughWordsException() {
        super(ResourceBundle.getBundle("com.xeppaka.sentence.messages").getString("notEnoughWords"));
    }
}
