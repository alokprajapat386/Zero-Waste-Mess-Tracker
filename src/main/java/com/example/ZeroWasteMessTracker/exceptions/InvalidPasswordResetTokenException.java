package com.example.ZeroWasteMessTracker.exceptions;

public class InvalidPasswordResetTokenException extends RuntimeException {
    public InvalidPasswordResetTokenException() {
        super("Invalid password reset token");
    }
}
