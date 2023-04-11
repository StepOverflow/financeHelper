package ru.shapovalov.service;

import ru.shapovalov.converter.UserModelToUserDtoConverter;
import ru.shapovalov.dao.UserDao;

public class ServiceFactory {
    private static UserAuthService userAuthService;

    public static UserAuthService getUserAuthService() {
        if (userAuthService == null) {
            userAuthService = new UserAuthService(
                    new UserDao(),
                    new Md5DigestService(),
                    new UserModelToUserDtoConverter()
            );
        }
        return userAuthService;
    }
}