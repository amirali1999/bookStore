package com.example.bookStore.exception.type;

public class SignUpException extends RuntimeException{
    private String message;
    public SignUpException(){}
    public SignUpException(String message){
        super(message);
        this.message = message;
    }
}
