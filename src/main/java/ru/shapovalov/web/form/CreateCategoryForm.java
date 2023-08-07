package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateCategoryForm {

    @NotEmpty
    private String name;
}