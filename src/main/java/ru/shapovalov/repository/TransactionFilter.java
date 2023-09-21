package ru.shapovalov.repository;

import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Timestamp;

@Data
@Accessors(chain = true)
public class TransactionFilter {
    private Integer minAmountPaid;
    private Integer maxAmountPaid;
    private Timestamp minCreatedDate;
    private Timestamp maxCreatedDate;
}