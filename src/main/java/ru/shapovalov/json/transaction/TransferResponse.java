package ru.shapovalov.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TransferResponse {
    private int id;
    private Integer sender;
    private Integer recipient;
    private int sum;
    private Timestamp createdDate;
}