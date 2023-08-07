package ru.shapovalov.service;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AccountDto {
    private Long id;
    private Long userId;
    private String accountName;
    private int balance;
}