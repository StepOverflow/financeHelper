package ru.shapovalov.api.json.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EditCategoryResponse {
    private Long id;
    private String name;
}