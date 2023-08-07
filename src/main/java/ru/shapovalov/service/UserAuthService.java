package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.Converter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.entity.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserAuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final Converter<User, UserDto> userDtoConverter;

    public Optional<UserDto> auth(String email, String password) {
        String hash = digestService.hex(password);
        User source = userDao.findByEmailAndHash(email, hash);
        return Optional.ofNullable(source)
                .map(userDtoConverter::convert);
    }

    public UserDto registration(String email, String password) {
        String hash = digestService.hex(password);

        User user = userDao.insert(email, hash);
        return userDtoConverter.convert(user);
    }

    public UserDto getByUserId(Long userId) {
        return userDtoConverter.convert(userDao.findByUserId(userId));
    }
}