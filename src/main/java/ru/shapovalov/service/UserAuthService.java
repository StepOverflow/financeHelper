package ru.shapovalov.service;

import ru.shapovalov.converter.UserModelToUserDtoConverter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.dao.UserModel;

import java.util.Optional;

public class UserAuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final UserModelToUserDtoConverter userDtoConverter;

    public UserAuthService() {
        this.userDao = new UserDao();
        this.digestService = new Md5DigestService();
        this.userDtoConverter = new UserModelToUserDtoConverter();
    }

    public Optional<UserDto> auth(String email, String password) {
        String hash = digestService.hex(password);
        return Optional.ofNullable(userDao.findByEmailAndHash(email, hash)).flatMap(source -> Optional.ofNullable(userDtoConverter.convert(source)));
    }

    public UserDto registration(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.insert(email, hash);
        return userDtoConverter.convert(userModel);
    }
}