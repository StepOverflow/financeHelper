package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EditAccountForm {

    @NotNull
    private Long accountId;

    @NotEmpty
    private String name;
}