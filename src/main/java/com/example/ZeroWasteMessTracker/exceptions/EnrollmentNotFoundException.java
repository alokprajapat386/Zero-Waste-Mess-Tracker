package com.example.ZeroWasteMessTracker.exceptions;

public class EnrollmentNotFoundException extends RuntimeException {
    public EnrollmentNotFoundException() {
        super("Enrollment not found");
    }
}
