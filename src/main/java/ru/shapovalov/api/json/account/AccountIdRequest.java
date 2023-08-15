package ru.shapovalov.api.json.account;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class AccountIdRequest {
    @Positive
    private Long accountId;
}