package ru.shapovalov.api.json.transaction;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class ReportRequest {
    @Positive
    private int days;
}