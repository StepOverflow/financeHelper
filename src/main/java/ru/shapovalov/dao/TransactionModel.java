package ru.shapovalov.dao;

import java.sql.Timestamp;

public class TransactionModel {
    private int id;
    private Integer sender;
    private Integer recipient;
    private int sum;
    private Timestamp createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    public Integer getRecipient() {
        return recipient;
    }

    public void setRecipient(Integer recipient) {
        this.recipient = recipient;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", sum=" + sum +
                ", createdDate=" + createdDate +
                '}';
    }
}