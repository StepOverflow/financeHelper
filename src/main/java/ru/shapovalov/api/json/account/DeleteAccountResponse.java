package ru.shapovalov.api.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteAccountResponse {
   private boolean isDeleted;
}