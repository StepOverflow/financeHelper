package ru.shapovalov.api.json.user;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
}