package ru.shapovalov.service;

import java.sql.Timestamp;

public class TransactionDto {
    private int id;
    private Integer sender;
    private Integer recipient;
    private int sum;
    private Timestamp timestamp;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", sum=" + sum +
                ", timestamp=" + timestamp +
                '}';
    }
}