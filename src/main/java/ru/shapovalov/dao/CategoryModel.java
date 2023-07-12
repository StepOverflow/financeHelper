package ru.shapovalov.dao;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CategoryModel {
    private int id;
    private String name;
    private int userId;
}