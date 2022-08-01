package com.github.moonproof.uh.exception;

public abstract class UhIncorrectDataException extends RuntimeException {

    public UhIncorrectDataException(String message) {
        super(message);
    }
}
