package org.deblock.exercise.exception;

import org.deblock.exercise.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<ApiResponse<Object>> handleViolationException(ConstraintViolationException ex) {
        return new ResponseEntity<>(ApiResponse.builder().message(ex.getMessage()).error(true).data(null).build(), HttpStatus.BAD_REQUEST);
    }
}
