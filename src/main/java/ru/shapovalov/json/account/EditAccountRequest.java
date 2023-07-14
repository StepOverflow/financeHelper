package ru.shapovalov.json.account;

import lombok.Data;

@Data
public class EditAccountRequest {
    private int id;
    private String name;
}