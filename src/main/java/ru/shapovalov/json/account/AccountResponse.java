package ru.shapovalov.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountResponse {
    private int id;
    private String accountName;
    private int balance;
}