package ru.shapovalov.json.account;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteAccountResponse {
   private boolean isDeleted;
}