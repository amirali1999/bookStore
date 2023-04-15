package com.example.bookStore.exception.handler;

import com.example.bookStore.exception.type.SkillWasDeletedException;
import com.example.bookStore.response.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class SkillWasDeletedExceptionHandler {
    @ExceptionHandler(value = SkillWasDeletedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public @ResponseBody ResponseEntity<?> handleSkillWasDeletedException(SkillWasDeletedException ex,
                                                                          WebRequest request) {
        return new Response(HttpStatus.FORBIDDEN, ex.getMessage(),null,0).createResponseEntity();
    }
}
