package ru.shapovalov.api.json.user;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;
}