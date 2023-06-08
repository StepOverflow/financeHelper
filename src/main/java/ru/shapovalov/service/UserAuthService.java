package ru.shapovalov.service;

import ru.shapovalov.converter.Converter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.dao.UserModel;

import java.util.Optional;

public class UserAuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final Converter<UserModel, UserDto> userDtoConverter;

    public UserAuthService(UserDao userDao, DigestService digestService, Converter<UserModel, UserDto> userDtoConverter) {
        this.userDao = userDao;
        this.digestService = digestService;
        this.userDtoConverter = userDtoConverter;
    }

    public Optional<UserDto> auth(String email, String password) {
        String hash = digestService.hex(password);
        UserModel source = userDao.findByEmailAndHash(email, hash);
        return Optional.ofNullable(source)
                .map(userDtoConverter::convert);
    }

    public UserDto registration(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.insert(email, hash);
        return userDtoConverter.convert(userModel);
    }
}