package ru.shapovalov.service;

import ru.shapovalov.converter.UserModelToUserDtoConverter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.dao.UserModel;

import java.util.Optional;

public class UserAuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final UserModelToUserDtoConverter userDtoConverter;

    public UserAuthService(UserDao userDao, DigestService digestService, UserModelToUserDtoConverter userDtoConverter) {
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