package ru.shapovalov.api.json.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateAccountRequest {
    @NotNull
    private String name;
}