package ru.shapovalov.api.json.user;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}