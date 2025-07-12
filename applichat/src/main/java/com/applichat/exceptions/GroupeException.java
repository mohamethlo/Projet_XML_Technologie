package com.applichat.exceptions;


public class GroupeException extends RuntimeException {

    public GroupeException(String message) {
        super(message);
    }

    public GroupeException(String message, Throwable cause) {
        super(message, cause);
    }
}
