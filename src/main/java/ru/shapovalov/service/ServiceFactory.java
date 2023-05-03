package ru.shapovalov.service;

import static ru.shapovalov.converter.ConverterFactory.*;
import static ru.shapovalov.dao.DaoFactory.*;

public class ServiceFactory {
    private static UserAuthService userAuthService;
    private static AccountService accountService;
    private static CategoryService categoryService;

    public static CategoryService getCategoryService() {
        if (categoryService == null) {
            categoryService = new CategoryService(
                    getCategoryDao(),
                    getCategoryDtoConverter()
            );
        }
        return categoryService;
    }

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(
                    getAccountDao(),
                    getAccountDtoConverter()
            );
        }
        return accountService;
    }

    public static UserAuthService getUserAuthService() {
        if (userAuthService == null) {
            userAuthService = new UserAuthService(
                    getUserDao(),
                    new Md5DigestService(),
                    getUserDtoConverter()
            );
        }
        return userAuthService;
    }
}
