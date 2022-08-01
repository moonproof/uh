package com.github.moonproof.uh.exception.handler;

import com.github.moonproof.uh.exception.InvalidUserStatusException;
import com.github.moonproof.uh.exception.UhIncorrectDataException;
import com.github.moonproof.uh.exception.UserNotFoundException;
import com.github.moonproof.uh.exception.dto.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class IncorrectDataHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(UhIncorrectDataException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidUserStatusException.class)
    protected ResponseEntity<ApiError> handleInvalidUserStatus(InvalidUserStatusException e) {
        ApiError err = new ApiError();
        err.setErrorMessage(e.getMessage());
        return ResponseEntity.badRequest().body(err);
    }
}

