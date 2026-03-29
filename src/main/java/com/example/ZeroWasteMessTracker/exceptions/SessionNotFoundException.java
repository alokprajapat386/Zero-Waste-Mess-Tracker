package com.example.ZeroWasteMessTracker.exceptions;

public class SessionNotFoundException extends RuntimeException {
    public SessionNotFoundException() {
        super("Session Not Found");
    }
}
