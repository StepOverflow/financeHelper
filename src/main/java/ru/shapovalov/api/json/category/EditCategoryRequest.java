package ru.shapovalov.api.json.category;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
public class EditCategoryRequest {
    @Positive
    private Long id;

    @NotNull
    @Size(min = 2, message = "Не меньше 2х символов")
    private String name;
}