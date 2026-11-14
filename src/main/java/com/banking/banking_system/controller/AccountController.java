package com.banking.banking_system.controller;

import com.banking.banking_system.dto.CreateAccountRequest;
import com.banking.banking_system.dto.AccountResponse;
import com.banking.banking_system.dto.TransactionRequest;
import com.banking.banking_system.dto.TransactionResponse;
import com.banking.banking_system.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<AccountResponse> createAccount(@Valid @RequestBody CreateAccountRequest createAccountRequest) {
        return new ResponseEntity<AccountResponse>(
                accountService.openNewAccount(createAccountRequest),
                org.springframework.http.HttpStatus.CREATED);
    }

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable("accountId") Long accountId) {
        return new ResponseEntity<AccountResponse>(
                accountService.getAccountDetails(accountId),
                HttpStatus.OK
        );
    }

    @PostMapping("/accounts/{accountId}/withdrawals")
    public ResponseEntity<TransactionResponse> withdraw(@PathVariable("accountId") Long accountId,
                                                        @Valid @RequestBody TransactionRequest request) {
        return new ResponseEntity<TransactionResponse>(
                accountService.performTransaction(request, accountId),
                HttpStatus.OK);
    }
}
