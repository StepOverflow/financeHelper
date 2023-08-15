package ru.shapovalov.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditAccountResponse {
    private Long id;
    private String accountName;
}