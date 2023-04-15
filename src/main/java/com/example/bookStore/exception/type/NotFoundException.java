package com.example.bookStore.exception.type;

public class NotFoundException extends Exception{
    private String message;
    public NotFoundException(){
    }
    public NotFoundException(String message){
        super(message);
        this.message = message;
    }
}
