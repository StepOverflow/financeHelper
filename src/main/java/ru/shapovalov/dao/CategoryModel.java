package ru.shapovalov.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryModel {
    private int id;
    private String name;
    private int userId;
}