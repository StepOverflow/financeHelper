package ru.shapovalov.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountModel {
    private int id;
    private int userId;
    private String accountName;
    private int balance;
}