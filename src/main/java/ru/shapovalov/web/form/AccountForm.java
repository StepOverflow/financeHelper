package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AccountForm {

    @NotEmpty(message = "Введите имя аккаунта")
    private String name;
}