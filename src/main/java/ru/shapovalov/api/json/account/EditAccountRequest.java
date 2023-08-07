package ru.shapovalov.api.json.account;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditAccountRequest {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}