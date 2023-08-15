package ru.shapovalov.api.json.category;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddCategoryResponse {
    private int id;
    private String name;
}