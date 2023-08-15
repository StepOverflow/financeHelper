package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EditCategoryForm {

    @NotNull
    private Long categoryId;

    @NotEmpty
    private String name;
}