package ru.shapovalov.api.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class TransferResponse {
    private int id;
    private Long sender;
    private Long recipient;
    private int sum;
    private Timestamp createdDate;
}