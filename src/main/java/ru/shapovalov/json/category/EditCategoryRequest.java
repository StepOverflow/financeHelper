package ru.shapovalov.json.category;

import lombok.Data;

@Data
public class EditCategoryRequest {
    private int id;
    private String name;
}