package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class LoginForm {
    @Email
    @NotEmpty
    private String email;

    @NotEmpty
    @Size(min = 2, max = 30)
    private String password;

    private String name;
}