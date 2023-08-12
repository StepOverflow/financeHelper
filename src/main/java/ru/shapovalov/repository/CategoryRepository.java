package ru.shapovalov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shapovalov.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByUserId(Long userId);

    Category findByIdAndUserId(Long categoryId, Long userId);
}