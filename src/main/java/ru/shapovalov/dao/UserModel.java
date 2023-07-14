package ru.shapovalov.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserModel {
    private int id;
    private String email;
    private String password;
}