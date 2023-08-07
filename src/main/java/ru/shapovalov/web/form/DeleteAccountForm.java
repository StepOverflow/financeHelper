package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteAccountForm {

    @NotNull
    private Long accountId;
}