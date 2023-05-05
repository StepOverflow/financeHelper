package ru.shapovalov.service;

import java.util.Objects;

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

    public String getName() {
        return accountName;
    }

    public void setName(String accountName) {
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
        return "AccountDto{" + "id=" + id + ", userId=" + userId + ", accountName='" + accountName + '\'' + ", balance=" + balance + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountDto that = (AccountDto) o;
        return id == that.id && userId == that.userId && balance == that.balance && Objects.equals(accountName, that.accountName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, accountName, balance);
    }
}