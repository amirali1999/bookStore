package com.example.bookStore.exception.handler;

import com.example.bookStore.exception.type.EmptyFieldException;
import com.example.bookStore.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmptyFieldExceptionHandler {
    @ExceptionHandler(value = EmptyFieldException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public @ResponseBody ResponseEntity<?> handlerEmptyFieldException(EmptyFieldException ex) {
        return new Response(HttpStatus.NOT_ACCEPTABLE, ex.getMessage(), null, 0).createResponseEntity();
    }
}
