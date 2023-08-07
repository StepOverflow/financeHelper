package ru.shapovalov.entity.id;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class TransactionCategoryId implements Serializable {
    private Long transaction;
    private Long category;
}
