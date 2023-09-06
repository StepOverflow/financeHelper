package ru.shapovalov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shapovalov.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
}