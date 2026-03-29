package com.example.ZeroWasteMessTracker.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super("User not found");
    }
}
