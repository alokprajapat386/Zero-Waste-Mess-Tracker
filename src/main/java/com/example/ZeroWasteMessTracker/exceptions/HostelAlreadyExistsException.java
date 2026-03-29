package com.example.ZeroWasteMessTracker.exceptions;

public class HostelAlreadyExistsException extends RuntimeException {
    public HostelAlreadyExistsException() {
        super("Hostel with this id already exists");
    }
}
