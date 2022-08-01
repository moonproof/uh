package com.github.moonproof.uh.exception.handler;

import com.github.moonproof.uh.exception.dto.ApiError;
import com.github.moonproof.uh.exception.file.FileNotSavedException;
import com.github.moonproof.uh.exception.file.IncorrectImageFormatException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class FileNotSavedHandlerTest {

    private final String message = "Error message";

    @InjectMocks
    private FileNotSavedHandler fileNotSavedHandler;

    @Test
    public void handleIncorrectImageFormatPositive() {
        IncorrectImageFormatException exception = new IncorrectImageFormatException(message);
        ApiError apiError = new ApiError(message);

        ResponseEntity<ApiError> responseEntity = fileNotSavedHandler.handleIncorrectImageFormat(exception);

        assertEquals(400, responseEntity.getStatusCodeValue());
        assertEquals(apiError, responseEntity.getBody());
    }

    @Test
    public void handleFileNotSavedPositive() {
        FileNotSavedException exception = new FileNotSavedException(message);
        ApiError apiError = new ApiError(message);

        ResponseEntity<ApiError> responseEntity = fileNotSavedHandler.handleFileNotSaved(exception);

        assertEquals(500, responseEntity.getStatusCodeValue());
        assertEquals(apiError, responseEntity.getBody());
    }

}