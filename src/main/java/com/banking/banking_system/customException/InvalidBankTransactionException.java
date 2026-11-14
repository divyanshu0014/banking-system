package com.banking.banking_system.customException;

public class InvalidBankTransactionException extends RuntimeException {
    public InvalidBankTransactionException(String message) {
        super(message);
    }
}
