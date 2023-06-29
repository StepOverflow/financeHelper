package ru.shapovalov.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryDto {
    private int id;
    private String name;
    private int userId;
}