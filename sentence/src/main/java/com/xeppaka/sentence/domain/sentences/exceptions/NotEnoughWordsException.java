package com.xeppaka.sentence.domain.sentences.exceptions;

import java.util.ResourceBundle;

/**
 * Exception is thrown when there are not enough words in the system to generate the sentence.
 */
public class NotEnoughWordsException extends Exception {
    public NotEnoughWordsException() {
        super(ResourceBundle.getBundle("com.xeppaka.sentence.messages").getString("notEnoughWords"));
    }
}
