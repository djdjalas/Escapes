package com.secretescapes.entitiy;

public class Account {
    private static long NUMBER_OF_ACCOUNTS = 0;
    private String accountType;
    private User accountHolder;
    private final double overdraftLimit = 0;
    private double balance;
    private Email email;

    public Account(String accountType, User accountHolder, Email email) {
        this.accountType = accountType;
        this.accountHolder = accountHolder;
        this.balance = 200.00; // initial balance
        this.email = email;

    }

    public Account(){}

    public static long getNumberOfCreatedAccounts() {
        return NUMBER_OF_ACCOUNTS;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public User getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(User accountHolder) {
        this.accountHolder = accountHolder;
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

}
