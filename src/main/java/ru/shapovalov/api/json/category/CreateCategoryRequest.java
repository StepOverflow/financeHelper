package ru.shapovalov.api.json.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryRequest {
    @NotNull
    private String name;
}