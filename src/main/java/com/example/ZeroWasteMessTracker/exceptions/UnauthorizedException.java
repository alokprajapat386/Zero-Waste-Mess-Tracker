package com.example.ZeroWasteMessTracker.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("Not authorized");
    }
}
