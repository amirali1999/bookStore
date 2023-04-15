package com.example.bookStore.exception.type;

public class InvalidIdException extends Exception{
    private String message;
    public InvalidIdException(){
    }
    public InvalidIdException(String message){
        super(message);
        this.message = message;
    }
}
