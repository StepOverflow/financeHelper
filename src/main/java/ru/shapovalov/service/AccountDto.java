package ru.shapovalov.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountDto {
    private int id;
    private int userId;
    private String accountName;
    private int balance;
}