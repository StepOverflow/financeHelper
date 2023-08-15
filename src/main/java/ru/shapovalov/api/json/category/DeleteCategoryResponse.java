package ru.shapovalov.api.json.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteCategoryResponse {
    private boolean isDeleted;
}