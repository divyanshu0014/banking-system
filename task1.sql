CREATE
DATABASE IF NOT EXISTS banking_system;
USE
banking_system;

CREATE TABLE branch
(
    branch_id     BIGINT AUTO_INCREMENT PRIMARY KEY,
    branch_name   VARCHAR(255) NOT NULL,
    branch_status ENUM('ACTIVE', 'CLOSED') NOT NULL
);

CREATE TABLE legal_person_identity
(
    legal_person_identity_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    identity_type            VARCHAR(255) NOT NULL,
    identity_number          VARCHAR(255) NOT NULL UNIQUE,
    verification_status      ENUM(
        'PENDING_VERIFICATION',
        'VERIFIED',
        'VERIFICATION_FAILED'
    ) NOT NULL
);

CREATE TABLE legal_person
(
    legal_person_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                     VARCHAR(255) NOT NULL,
    date_of_birth            DATE         NOT NULL,
    phone_number             VARCHAR(255),
    legal_person_identity_id BIGINT       NOT NULL UNIQUE,
    CONSTRAINT fk_person_identity
        FOREIGN KEY (legal_person_identity_id)
            REFERENCES legal_person_identity (legal_person_identity_id)
);

CREATE TABLE address
(
    address_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    street          VARCHAR(255) NOT NULL,
    city            VARCHAR(255) NOT NULL,
    state           VARCHAR(255) NOT NULL,
    postal_code     VARCHAR(255) NOT NULL,
    country         VARCHAR(255) NOT NULL,
    is_primary      BOOLEAN      NOT NULL DEFAULT TRUE,
    legal_person_id BIGINT       NOT NULL UNIQUE,
    CONSTRAINT fk_address_person
        FOREIGN KEY (legal_person_id)
            REFERENCES legal_person (legal_person_id)
);

CREATE TABLE account
(
    account_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_number  VARCHAR(50) NOT NULL UNIQUE,
    account_status  ENUM('ACTIVE','INACTIVE') NOT NULL,
    account_type    ENUM('CHECKING','SAVINGS') NOT NULL,
    currency        ENUM('CAD','EUR','INR','USD')  NOT NULL,
    legal_person_id BIGINT      NOT NULL,
    branch_id       BIGINT      NOT NULL,
    CONSTRAINT fk_account_person
        FOREIGN KEY (legal_person_id)
            REFERENCES legal_person (legal_person_id),
    CONSTRAINT fk_account_branch
        FOREIGN KEY (branch_id)
            REFERENCES branch (branch_id)
);

CREATE TABLE balance
(
    account_id      BIGINT PRIMARY KEY,
    account_balance DECIMAL(19, 2) NOT NULL DEFAULT 0.00,
    updated_at      DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6) ON UPDATE CURRENT_TIMESTAMP(6),
    CONSTRAINT fk_balance_account
        FOREIGN KEY (account_id)
            REFERENCES account (account_id)
);

CREATE TABLE bank_transaction
(
    transaction_id    BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount            DECIMAL(19, 2) NOT NULL,
    created_at        DATETIME(6)   NOT NULL DEFAULT CURRENT_TIMESTAMP(6),
    status            ENUM('FAILED','PENDING','SUCCESS')   NOT NULL,
    transaction_type  ENUM('DEPOSIT','PAYMENT','TRANSFER','WITHDRAW')    NOT NULL,
    source_account_id BIGINT         NOT NULL,
    target_account_id BIGINT NULL,
    CONSTRAINT fk_transaction_source
        FOREIGN KEY (source_account_id)
            REFERENCES account (account_id),
    CONSTRAINT fk_transaction_target
        FOREIGN KEY (target_account_id)
            REFERENCES account (account_id),
    CONSTRAINT chk_transaction_amount
        CHECK (amount > 0)
);