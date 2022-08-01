package com.github.moonproof.uh.exception.handler;

import com.github.moonproof.uh.exception.InvalidUserStatusException;
import com.github.moonproof.uh.exception.UserNotFoundException;
import com.github.moonproof.uh.exception.dto.ApiError;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class IncorrectImageFormatExceptionTest {

    private final String message = "Error message";

    @InjectMocks
    private IncorrectDataHandler incorrectDataHandler;

    @Test
    public void handleUserNotFoundPositive() {
        UserNotFoundException userNotFoundException = new UserNotFoundException(message);

        ResponseEntity<Object> response = incorrectDataHandler.handleUserNotFound(userNotFoundException);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void handleInvalidUserStatusPositive() {
        InvalidUserStatusException userNotFoundException = new InvalidUserStatusException(message);
        ApiError error = new ApiError(message);

        ResponseEntity<ApiError> response = incorrectDataHandler.handleInvalidUserStatus(userNotFoundException);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals(error, response.getBody());
    }

}