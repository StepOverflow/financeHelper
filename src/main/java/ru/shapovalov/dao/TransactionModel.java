package ru.shapovalov.dao;

import java.sql.Timestamp;

public class TransactionModel {
    private int id;
    private int sender;
    private int recipient;
    private int sum;
    private Timestamp timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getRecipient() {
        return recipient;
    }

    public void setRecipient(int recipient) {
        this.recipient = recipient;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public java.sql.Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(java.sql.Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionModel{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", sum=" + sum +
                ", timestamp=" + timestamp +
                '}';
    }
}
