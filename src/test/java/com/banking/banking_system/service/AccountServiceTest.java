package com.banking.banking_system.service;

import com.banking.banking_system.customException.InsufficientFundsException;
import com.banking.banking_system.customException.InvalidBankTransactionException;
import com.banking.banking_system.customException.ResourceNotFoundException;
import com.banking.banking_system.dto.*;
import com.banking.banking_system.entities.AccountEntity;
import com.banking.banking_system.entities.BalanceEntity;
import com.banking.banking_system.entities.BankTransactionEntity;
import com.banking.banking_system.entities.BranchEntity;
import com.banking.banking_system.entities.LegalPersonEntity;
import com.banking.banking_system.entities.LegalPersonIdentityEntity;
import com.banking.banking_system.entities.enums.AccountTypeEnum;
import com.banking.banking_system.entities.enums.CurrencyEnum;
import com.banking.banking_system.entities.enums.TransactionTypeEnum;
import com.banking.banking_system.entities.enums.VerificationStatusEnum;
import com.banking.banking_system.repository.AccountRepository;
import com.banking.banking_system.repository.AddressRepository;
import com.banking.banking_system.repository.BalanceRepository;
import com.banking.banking_system.repository.BranchRepository;
import com.banking.banking_system.repository.LegalPersonIdentityRepository;
import com.banking.banking_system.repository.LegalPersonRepository;
import com.banking.banking_system.repository.TransactionRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private BranchRepository branchRepository;
    @Mock
    private LegalPersonIdentityRepository identityRepository;

    @Mock
    private LegalPersonRepository legalPersonRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private BalanceRepository balanceRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private AccountEntity account;
    private BranchEntity branch;
    private LegalPersonEntity legalPerson;
    private LegalPersonIdentityEntity identity;

    @Before
    public void setUp() {
        account = mock(AccountEntity.class);
        branch = mock(BranchEntity.class);
        legalPerson = mock(LegalPersonEntity.class);
        identity = mock(LegalPersonIdentityEntity.class);
    }

    // 1. Account does not exist
    @Test
    public void getAccountDetails_whenAccountMissing_throwsException() {
        Long accountId = 100L;

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        try {
            accountService.getAccountDetails(accountId);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException exception) {
            assertEquals("Account not found", exception.getMessage());
        }

        verify(balanceRepository, never()).findById(anyLong());
    }

    // 2. Balance does not exist
    @Test
    public void getAccountDetails_whenBalanceMissing_throwsException() {
        Long accountId = 100L;

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(account.getAccountId()).thenReturn(accountId);

        when(balanceRepository.findById(accountId))
                .thenReturn(Optional.empty());

        try {
            accountService.getAccountDetails(accountId);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException exception) {
            assertEquals(
                    "Balance not found for passed account id",
                    exception.getMessage()
            );
        }
    }

    // 3. Withdrawal succeeds
    @Test
    public void performTransaction_whenSufficientFunds_withdrawsSuccessfully() {
        Long accountId = 100L;
        BigDecimal initialBalance = new BigDecimal("100.00");
        BigDecimal withdrawalAmount = new BigDecimal("30.00");
        BigDecimal expectedBalance = new BigDecimal("70.00");

        TransactionRequest request = mock(TransactionRequest.class);

        when(request.getTransactionType())
                .thenReturn(TransactionTypeEnum.WITHDRAW);

        when(request.getAmount()).thenReturn(withdrawalAmount);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(account.getAccountId()).thenReturn(accountId);

        BalanceEntity balance =
                new BalanceEntity(account, initialBalance);

        when(balanceRepository.findByIdForUpdate(accountId))
                .thenReturn(Optional.of(balance));

        TransactionResponse response =
                accountService.performTransaction(request, accountId);

        assertNotNull(response);
        assertEquals(0, expectedBalance.compareTo(balance.getBalance()));

        verify(balanceRepository).save(balance);
        verify(transactionRepository).save(
                any(BankTransactionEntity.class)
        );
    }

    // 4. Withdrawal exceeds available balance
    @Test
    public void performTransaction_whenInsufficientFunds_throwsException() {
        Long accountId = 100L;

        TransactionRequest request = mock(TransactionRequest.class);

        when(request.getTransactionType())
                .thenReturn(TransactionTypeEnum.WITHDRAW);

        when(request.getAmount())
                .thenReturn(new BigDecimal("150.00"));

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        BalanceEntity balance =
                new BalanceEntity(account, new BigDecimal("100.00"));

        when(balanceRepository.findByIdForUpdate(accountId))
                .thenReturn(Optional.of(balance));

        try {
            accountService.performTransaction(request, accountId);
            fail("Expected InsufficientFundsException");
        } catch (InsufficientFundsException exception) {
            assertEquals(
                    "Insufficient funds in the source account",
                    exception.getMessage()
            );
        }

        assertEquals(
                0,
                new BigDecimal("100.00").compareTo(balance.getBalance())
        );

        verify(balanceRepository, never()).save(any(BalanceEntity.class));
        verify(transactionRepository, never())
                .save(any(BankTransactionEntity.class));
    }

    // 5. Source account does not exist
    @Test
    public void performTransaction_whenSourceAccountMissing_throwsException() {
        Long accountId = 100L;

        TransactionRequest request = mock(TransactionRequest.class);

        when(request.getTransactionType())
                .thenReturn(TransactionTypeEnum.WITHDRAW);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());

        try {
            accountService.performTransaction(request, accountId);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException exception) {
            assertEquals(
                    "Source account not found",
                    exception.getMessage()
            );
        }

        verify(balanceRepository, never()).findByIdForUpdate(anyLong());
    }

    // 6. Source balance does not exist
    @Test
    public void performTransaction_whenSourceBalanceMissing_throwsException() {
        Long accountId = 100L;

        TransactionRequest request = mock(TransactionRequest.class);

        when(request.getTransactionType())
                .thenReturn(TransactionTypeEnum.WITHDRAW);

        when(accountRepository.findById(accountId))
                .thenReturn(Optional.of(account));

        when(balanceRepository.findByIdForUpdate(accountId))
                .thenReturn(Optional.empty());

        try {
            accountService.performTransaction(request, accountId);
            fail("Expected ResourceNotFoundException");
        } catch (ResourceNotFoundException exception) {
            assertEquals(
                    "Source account not found",
                    exception.getMessage()
            );
        }

        verify(balanceRepository, never()).save(any(BalanceEntity.class));
        verify(transactionRepository, never())
                .save(any(BankTransactionEntity.class));
    }

    // 7. Unsupported transaction type
    @Test
    public void performTransaction_whenTransactionIsUnsupported_throwsException() {
        TransactionRequest request = mock(TransactionRequest.class);

        when(request.getTransactionType()).thenReturn(null);

        try {
            accountService.performTransaction(request, 100L);
            fail("Expected InvalidBankTransactionException");
        } catch (InvalidBankTransactionException exception) {
            assertEquals(
                    "Transaction type not supported",
                    exception.getMessage()
            );
        }

        verifyNoInteractions(accountRepository, balanceRepository,
                transactionRepository);
    }

    @Test
    public void createAccount_createsAccountSuccessfully() {
        CreateAccountRequest createAccountRequest = mock(CreateAccountRequest.class);
        Address address = mock(Address.class);
        BranchEntity branch = mock(BranchEntity.class);
        LegalPersonIdentityEntity legalPersonIdentity = mock(LegalPersonIdentityEntity.class);

        //Mock Branch Entity Related calls
        when(branchRepository.findById(anyLong()))
                .thenReturn(Optional.of(branch));

        //Mock Person Identity related calls
        when(createAccountRequest.getIdentityNumber()).thenReturn("123456789");
        when(createAccountRequest.getIdentityType()).thenReturn("Passport");

        //Mock LegalPerson related calls
        when(createAccountRequest.getLegalPersonName()).thenReturn("John Doe");
        when(createAccountRequest.getPhoneNumber()).thenReturn("123456789");
        when(createAccountRequest.getDateOfBirth()).thenReturn(LocalDate.parse("1999-02-14"));


        // Mock Address details
        when(createAccountRequest.getAddress()).thenReturn(address);
        when(address.getStreet()).thenReturn("123 Main St");
        when(address.getCity()).thenReturn("Main St");
        when(address.getState()).thenReturn("Main St");
        when(address.getPostalCode()).thenReturn("12345");
        when(address.getCountry()).thenReturn("INDIA");

        // Mock Account related calls
        when(accountRepository.existsByAccountNumber(anyString())).thenReturn(false);
        when(accountRepository.save(any())).thenReturn(account);
        when(createAccountRequest.getAccountType()).thenReturn(AccountTypeEnum.SAVINGS);
        when(createAccountRequest.getCurrency()).thenReturn(CurrencyEnum.INR);

        when(account.getLegalPerson()).thenReturn(legalPerson);
        when(account.getAccountNumber()).thenReturn("123456789");
        when(legalPerson.getLegalPersonIdentity()).thenReturn(legalPersonIdentity);
        when(legalPersonIdentity.getIdentityNumber()).thenReturn("123456789");
        when(legalPersonIdentity.getIdentityType()).thenReturn("Passport");
        when(legalPersonIdentity.getVerificationStatus()).thenReturn(VerificationStatusEnum.VERIFIED);

        when(account.getBranch()).thenReturn(branch);
        when(branch.getBranchId()).thenReturn(1L);
        when(branch.getBranchName()).thenReturn("Main Branch");


        AccountResponse account = accountService.openNewAccount(createAccountRequest);

        //verifies Account check and Persist happens least once
        verify(accountRepository, times(1)).existsByAccountNumber(anyString());
        verify(accountRepository, times(1)).save(any());

        //verifies balance was updated
        verify(balanceRepository, times(1)).save(any());

        //verifies legalPersonDetails, IdentityDetails and AddressDetails were saved
        verify(legalPersonRepository, times(1)).save(any());
        verify(identityRepository, times(1)).save(any());
        verify(addressRepository, times(1)).save(any());

        //verifies branchDetails were saved
        verify(branchRepository, times(1)).findById(anyLong());

        // Assert response is not null
        assertNotNull(account);

// Assert account details
        assertFalse(account.getAccountNumber().isEmpty());

// Assert branch details
        assertEquals(Long.valueOf(1L), account.getBranchId());
        assertEquals("Main Branch", account.getBranchName());

// Assert identity details
        assertEquals("123456789", account.getHolderIdentityDetails().getIdentityNumber());
        assertEquals("Passport", account.getHolderIdentityDetails().getIdentityType());
        assertEquals(
                VerificationStatusEnum.VERIFIED,
                account.getHolderIdentityDetails().getVerificationStatus());

    }
}