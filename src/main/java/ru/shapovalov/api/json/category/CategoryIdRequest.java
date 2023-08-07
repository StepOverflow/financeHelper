package ru.shapovalov.api.json.category;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class CategoryIdRequest {
    @NotNull
    private Long categoryId;
}