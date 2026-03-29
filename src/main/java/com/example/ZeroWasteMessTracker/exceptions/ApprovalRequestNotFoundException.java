package com.example.ZeroWasteMessTracker.exceptions;

public class ApprovalRequestNotFoundException extends RuntimeException {
    public ApprovalRequestNotFoundException() {

        super("Approval request not found");
    }
}
