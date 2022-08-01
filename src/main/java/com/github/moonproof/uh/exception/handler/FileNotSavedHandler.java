package com.github.moonproof.uh.exception.handler;

import com.github.moonproof.uh.exception.dto.ApiError;
import com.github.moonproof.uh.exception.file.FileNotSavedException;
import com.github.moonproof.uh.exception.file.IncorrectImageFormatException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class FileNotSavedHandler {

    @ExceptionHandler(IncorrectImageFormatException.class)
    protected ResponseEntity<ApiError> handleIncorrectImageFormat(IncorrectImageFormatException ex) {
        ApiError err = new ApiError(ex.getMessage());
        return ResponseEntity.badRequest().body(err);
    }

    @ExceptionHandler(FileNotSavedException.class)
    protected ResponseEntity<ApiError> handleFileNotSaved(FileNotSavedException ex) {
        ApiError err = new ApiError(ex.getMessage());
        return ResponseEntity.internalServerError().body(err);
    }

}
