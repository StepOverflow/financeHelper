package ru.shapovalov.service;

public class AccountDto {
    private int id;
    private int userId;
    private String accountName;
    private int balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
                "id=" + id +
                ", userId=" + userId +
                ", accountName='" + accountName + '\'' +
                ", balance=" + balance +
                '}';
    }
}