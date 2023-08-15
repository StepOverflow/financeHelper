package ru.shapovalov.api.json.account;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateAccountRequest {
    @NotNull
    @Size(min = 2, message = "Не меньше 2х символов")
    private String name;
}