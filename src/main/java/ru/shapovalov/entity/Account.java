package ru.shapovalov.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "accounts")
@NamedQueries({
        @NamedQuery(name = "Account.getAllByUserId", query = "SELECT a FROM Account AS a WHERE a.user.id = :user_id"),
        @NamedQuery(name = "Account.getBalance", query = "SELECT a FROM Account a WHERE  a.balance > :balance")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "balance")
    private int balance;

    @Column(name = "account_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", name='" + name + '\'' +
                '}';
    }
}