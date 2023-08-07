package ru.shapovalov.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount_paid")
    private int amountPaid;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @OneToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    @OneToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @ManyToMany
    @JoinTable(
            name = "transactions_categories",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories;

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amountPaid=" + amountPaid +
                ", createdDate=" + createdDate +
                ", fromAccount=" + fromAccount +
                ", toAccount=" + toAccount +
                ", categories=" + categories +
                '}';
    }
}
