package ru.shapovalov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shapovalov.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailAndPassword(String email, String password);
}