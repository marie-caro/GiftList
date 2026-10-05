package com.example.giftlist.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("Person with id " + id + " was not found");
    }
}
