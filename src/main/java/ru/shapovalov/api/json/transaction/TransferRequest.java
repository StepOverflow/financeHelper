package ru.shapovalov.api.json.transaction;

import lombok.Data;

import javax.validation.constraints.Positive;
import java.util.List;

@Data
public class TransferRequest {

    @Positive
    private Long fromAccountId;

    @Positive
    private Long toAccountId;

    @Positive(message = "Amount must be greater than zero")
    private int sum;

    private List<Long> categoryIds;
}