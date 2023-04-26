package ru.shapovalov.service;

import java.util.Optional;

public interface UserAuthService {
     Optional<UserDto> auth(String email, String password);
}
