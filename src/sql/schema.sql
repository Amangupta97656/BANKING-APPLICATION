CREATE DATABASE banking;
USE banking;

CREATE TABLE Users (
    AccountNumber BIGINT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    PIN_HASH VARCHAR(255) NOT NULL,
    Balance DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    WrongAttempts INT DEFAULT 0
);

CREATE TABLE Transactions (
    TransactionID INT AUTO_INCREMENT PRIMARY KEY,
    AccountNumber BIGINT,
    Type ENUM('Deposit', 'Withdrawal'),
    Amount DECIMAL(10,2) NOT NULL,
    BalanceAfter DECIMAL(10,2) NOT NULL,
    Timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (AccountNumber) REFERENCES Users(AccountNumber)
);


ALTER TABLE Users ADD COLUMN LockedUntil TIMESTAMP NULL DEFAULT NULL;
