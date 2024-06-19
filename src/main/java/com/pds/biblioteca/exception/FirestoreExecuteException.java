package com.pds.biblioteca.exception;

import lombok.Getter;

@Getter
public class FirestoreExecuteException extends RuntimeException {

    private final String error;

    public FirestoreExecuteException(String error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }
}