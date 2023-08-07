package ru.shapovalov.api.json.category;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.shapovalov.entity.Category;

import java.util.List;

@Data
@RequiredArgsConstructor
public class CategoriesResponse {
    private final Long userId;
    private final List<Category> categories;
}