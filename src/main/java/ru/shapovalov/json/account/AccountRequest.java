package ru.shapovalov.json.account;

import lombok.Data;

@Data
public class AccountRequest {
    private int id;
    private String accountName;
}