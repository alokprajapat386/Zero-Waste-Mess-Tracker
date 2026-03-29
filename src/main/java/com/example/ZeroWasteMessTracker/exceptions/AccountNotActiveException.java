package com.example.ZeroWasteMessTracker.exceptions;

public class AccountNotActiveException extends  RuntimeException{
    public AccountNotActiveException(){
        super("Account not approved by admin");
    }
}
