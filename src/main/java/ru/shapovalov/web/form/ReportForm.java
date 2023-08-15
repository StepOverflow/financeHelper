package ru.shapovalov.web.form;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class ReportForm {

    @Positive
    private int days;
}