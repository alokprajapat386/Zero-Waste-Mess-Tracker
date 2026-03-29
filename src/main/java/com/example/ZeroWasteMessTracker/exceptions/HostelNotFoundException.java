package com.example.ZeroWasteMessTracker.exceptions;

public class HostelNotFoundException extends RuntimeException {
    public HostelNotFoundException() {
        super("Hostel not found");
    }
}
