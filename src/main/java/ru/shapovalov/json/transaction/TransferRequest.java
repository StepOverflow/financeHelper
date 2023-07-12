package ru.shapovalov.json.transaction;

import lombok.Data;

import java.util.List;

@Data
public class TransferRequest {
    private Integer sender;
    private Integer recipient;
    private int sum;
    private List<Integer> categoryIds;
}