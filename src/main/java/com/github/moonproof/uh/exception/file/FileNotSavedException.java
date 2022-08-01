package com.github.moonproof.uh.exception.file;

public class FileNotSavedException extends RuntimeException {
    public FileNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotSavedException(String message) {
        super(message);
    }
}
