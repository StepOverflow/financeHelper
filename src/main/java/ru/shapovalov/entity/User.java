package ru.shapovalov.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "users")
@NamedQueries({
        @NamedQuery(name = "Users.getByEmailAndPassword", query = "SELECT u FROM User AS u WHERE u.email = :email and u.password = :password"),
        @NamedQuery(name = "Users.getById", query = "SELECT u FROM User AS u WHERE u.id = :id")
})
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}