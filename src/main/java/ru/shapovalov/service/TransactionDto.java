package ru.shapovalov.service;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class TransactionDto {
    private Long id;
    private Long sender;
    private Long recipient;
    private int sum;
    private Timestamp createdDate;
}