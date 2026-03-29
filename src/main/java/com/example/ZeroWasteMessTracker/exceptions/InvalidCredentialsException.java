package com.example.ZeroWasteMessTracker.exceptions;

public class InvalidCredentialsException extends  RuntimeException{
    public InvalidCredentialsException(){
        super("Invalid username or password");
    }
}
