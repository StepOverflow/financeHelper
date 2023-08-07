package ru.shapovalov.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddAccountResponse {
    private int id;
    private String accountName;
    private int balance;
}