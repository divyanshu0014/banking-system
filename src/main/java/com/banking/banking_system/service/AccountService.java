package com.banking.banking_system.service;

import com.banking.banking_system.customException.InsufficientFundsException;
import com.banking.banking_system.customException.InvalidBankTransactionException;
import com.banking.banking_system.customException.ResourceNotFoundException;
import com.banking.banking_system.dto.*;
import com.banking.banking_system.entities.*;
import com.banking.banking_system.entities.enums.AccountStatusEnum;
import com.banking.banking_system.entities.enums.TransactionStatusEnum;
import com.banking.banking_system.entities.enums.TransactionTypeEnum;
import com.banking.banking_system.entities.enums.VerificationStatusEnum;
import com.banking.banking_system.repository.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AccountService {

    private final BranchRepository branchRepository;
    private final LegalPersonIdentityRepository identityRepository;
    private final LegalPersonRepository legalPersonRepository;
    private final AddressRepository addressRepository;
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(
            BranchRepository branchRepository,
            LegalPersonIdentityRepository identityRepository,
            LegalPersonRepository legalPersonRepository,
            AddressRepository addressRepository,
            AccountRepository accountRepository,
            BalanceRepository balanceRepository,
            TransactionRepository transactionRepository) {
        this.branchRepository = branchRepository;
        this.identityRepository = identityRepository;
        this.legalPersonRepository = legalPersonRepository;
        this.addressRepository = addressRepository;
        this.accountRepository = accountRepository;
        this.balanceRepository = balanceRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public AccountResponse openNewAccount(CreateAccountRequest request) {
        BranchEntity branch = branchRepository.findById(request.getBranchId())
                .orElseThrow(() -> new ResourceNotFoundException("Branch not found"));

        LegalPersonIdentityEntity legalPersonIdentity = new LegalPersonIdentityEntity(
                request.getIdentityType(),
                request.getIdentityNumber(),
                VerificationStatusEnum.PENDING_VERIFICATION
        );
        legalPersonIdentity = identityRepository.save(legalPersonIdentity);

        LegalPersonEntity legalPerson = new LegalPersonEntity(
                request.getLegalPersonName(),
                request.getDateOfBirth(),
                legalPersonIdentity,
                request.getPhoneNumber()

        );
        legalPerson = legalPersonRepository.save(legalPerson);

        AddressEntity address = new AddressEntity(
                legalPerson,
                request.getAddress().getStreet(),
                request.getAddress().getCity(),
                request.getAddress().getState(),
                request.getAddress().getPostalCode(),
                request.getAddress().getCountry(),
                true
        );
        addressRepository.save(address);

        String accountNumber = "100" + ThreadLocalRandom.current()
                .nextInt(100_000, 1_000_000);

        while (accountRepository.existsByAccountNumber(accountNumber)) {
            accountNumber = "100" + ThreadLocalRandom.current()
                    .nextInt(100_000, 1_000_000);
        }

        AccountEntity account = new AccountEntity(
                accountNumber,
                legalPerson,
                branch,
                AccountStatusEnum.INACTIVE,
                request.getAccountType(),
                request.getCurrency()
        );

        account = accountRepository.save(account);

        BalanceEntity balanceEntity = new BalanceEntity(account, BigDecimal.ZERO);
        balanceRepository.save(balanceEntity);

        HolderIdentityDetails holderIdentityDetails = new HolderIdentityDetails(
                account.getLegalPerson().getLegalPersonIdentity().getIdentityType(),
                account.getLegalPerson().getLegalPersonIdentity().getIdentityNumber(),
                account.getLegalPerson().getLegalPersonIdentity().getVerificationStatus()
        );

        return new AccountResponse(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getLegalPerson().getName(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getBranch().getBranchId(),
                account.getBranch().getBranchName(),
                balanceEntity.getBalance(),
                account.getCurrency(),
                holderIdentityDetails

        );
    }

    public AccountResponse getAccountDetails(Long accountId) {
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        BalanceEntity balanceEntity = balanceRepository.findById(account.getAccountId()).orElseThrow(
                () -> new ResourceNotFoundException("Balance not found for passed account id"));

        HolderIdentityDetails holderIdentityDetails = new HolderIdentityDetails(
                account.getLegalPerson().getLegalPersonIdentity().getIdentityType(),
                account.getLegalPerson().getLegalPersonIdentity().getIdentityNumber(),
                account.getLegalPerson().getLegalPersonIdentity().getVerificationStatus()
        );

        return new AccountResponse(
                account.getAccountId(),
                account.getAccountNumber(),
                account.getLegalPerson().getName(),
                account.getAccountType(),
                account.getAccountStatus(),
                account.getBranch().getBranchId(),
                account.getBranch().getBranchName(),
                balanceEntity.getBalance(),
                account.getCurrency(),
                holderIdentityDetails);
    }

    @Transactional
    public TransactionResponse performTransaction(TransactionRequest request, Long sourceAccountId) {
        if (request.getTransactionType() == TransactionTypeEnum.WITHDRAW) {
            return withdrawalOperation(request, sourceAccountId);
        } else {
            throw new InvalidBankTransactionException("Transaction type not supported");
        }
    }

    private TransactionResponse withdrawalOperation(TransactionRequest request, Long sourceAccountId) {
        AccountEntity sourceAccount = accountRepository.findById(sourceAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

        BalanceEntity sourceAccountBalance = balanceRepository.findByIdForUpdate(sourceAccountId)
                .orElseThrow(() -> new ResourceNotFoundException("Source account not found"));

        if (!checkBalanceForSourceAccount(sourceAccountBalance, request.getAmount())) {
            throw new InsufficientFundsException("Insufficient funds in the source account");
        }

        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance().subtract(request.getAmount()));
        balanceRepository.save(sourceAccountBalance);


        BankTransactionEntity transaction = new BankTransactionEntity(
                TransactionTypeEnum.WITHDRAW,
                request.getAmount(),
                TransactionStatusEnum.SUCCESS,
                sourceAccount,
                null
        );

        transactionRepository.save(transaction);

        return new TransactionResponse(
                transaction.getTransactionId(),
                transaction.getSourceAccount().getAccountId(),
                transaction.getStatus(),
                sourceAccountBalance.getBalance()
        );
    }

    private boolean checkBalanceForSourceAccount(BalanceEntity sourceAccountBalance, BigDecimal amount) {
        return sourceAccountBalance.getBalance().compareTo(amount) >= 0;
    }
}
