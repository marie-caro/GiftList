package com.example.giftlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GiftNotFoundException extends RuntimeException {
    public GiftNotFoundException(Long id) {
        super("Person with id " + id + " was not found");
    }
}
