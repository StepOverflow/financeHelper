package ru.shapovalov.entity;

import lombok.Data;
import ru.shapovalov.entity.id.TransactionCategoryId;

import javax.persistence.*;

@Data
@Entity
@Table(name = "transactions_categories")
@IdClass(TransactionCategoryId.class)
public class TransactionCategory {
    @Id
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;

    @Id
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
