package com.example.ZeroWasteMessTracker.exceptions;

public class RatingsNotFoundException extends RuntimeException {
    public RatingsNotFoundException() {
        super("Ratings not found");
    }
}
