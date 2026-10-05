package com.example.giftlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class GiftListNotFoundException extends RuntimeException {
    public GiftListNotFoundException(Long id) {
        super("The list with id: " + id + " was not found.");
    }
}
