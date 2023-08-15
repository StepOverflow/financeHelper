package ru.shapovalov.api.json.category;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class CategoryIdRequest {
    @Positive
    private Long categoryId;
}