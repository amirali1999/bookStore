package com.example.bookStore.exception.type;

public class EmptyFieldException extends Exception{
    private String message;
    public EmptyFieldException(){
    }
    public EmptyFieldException(String message){
        super(message);
        this.message = message;
    }
}
