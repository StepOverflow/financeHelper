package ru.shapovalov.json.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class ReportResponse {
    private Map<String, Long> report;
}