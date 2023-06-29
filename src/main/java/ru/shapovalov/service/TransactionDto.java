package ru.shapovalov.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private int id;
    private Integer sender;
    private Integer recipient;
    private int sum;
    private Timestamp createdDate;
}