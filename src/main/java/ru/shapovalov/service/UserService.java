package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.Converter;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final DigestService digestService;
    private final Converter<User, UserDto> userDtoConverter;

    public Optional<UserDto> auth(String email, String password) {
        String hash = digestService.hex(password);
        return userRepository.findByEmailAndPassword(email, hash)
                .map(userDtoConverter::convert);
    }

    public UserDto registration(String email, String password) {
        String hash = digestService.hex(password);
        User user = new User();
        user.setEmail(email);
        user.setPassword(hash);

        user = userRepository.save(user);

        return userDtoConverter.convert(user);
    }

    public UserDto getByUserId(Long userId) {
        return userRepository.findById(userId).map(userDtoConverter::convert).orElse(null);
    }
}