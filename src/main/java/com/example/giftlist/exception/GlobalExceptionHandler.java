package com.example.giftlist.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlePersonNotFound(PersonNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(404,  "Person Not Found", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(GiftNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGiftNotFound(GiftNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(404,  "Gift Not Found", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(GiftListNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGiftListNotFound(GiftListNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(404,  "GiftList Not Found", exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        ErrorResponse error = new ErrorResponse(400, "Validation Failed", message);
        return ResponseEntity.status(400).body(error);
    }
}
