package ru.shapovalov.api.json.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AccountIdRequest {
    @NotNull(message = "Account ID is required")
    private Long accountId;
}