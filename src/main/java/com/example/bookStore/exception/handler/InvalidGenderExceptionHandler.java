package com.example.bookStore.exception.handler;

import com.example.bookStore.exception.type.InvalidGenderException;
import com.example.bookStore.exception.type.InvalidIdException;
import com.example.bookStore.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

public class InvalidGenderExceptionHandler {
    @ExceptionHandler(value = InvalidGenderException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ResponseEntity<?> handlerInvalidGenderException(InvalidIdException ex) {
        return new Response(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(),null,0).createResponseEntity();
    }
}
