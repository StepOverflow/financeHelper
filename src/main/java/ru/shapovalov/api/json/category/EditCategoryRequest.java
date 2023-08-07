package ru.shapovalov.api.json.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EditCategoryRequest {
    @NotNull
    private Long id;

    @NotNull
    private String name;
}