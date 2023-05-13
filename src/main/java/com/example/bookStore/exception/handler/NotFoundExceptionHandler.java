package com.example.bookStore.exception.handler;

import com.example.bookStore.exception.type.NotFoundException;
import com.example.bookStore.response.Response;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
@Slf4j
public class NotFoundExceptionHandler {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ResponseEntity<?> handlerUserNotFoundException(NotFoundException ex) {
        log.error("user not found!");
        return new Response(HttpStatus.NOT_FOUND, ex.getMessage(),null,0).createResponseEntity();
    }
}
