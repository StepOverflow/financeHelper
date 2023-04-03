package service;

import converter.UserModelToUserDtoConverter;
import dao.UserDao;
import dao.UserModel;

public class AuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final UserModelToUserDtoConverter userDtoConverter;

    public AuthService() {
        this.userDtoConverter = new UserModelToUserDtoConverter();
        this.digestService = new Md5DigestService();
        this.userDao = new UserDao();

    }

    public UserDTO auth(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.findByEmailAndHash(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDtoConverter.convert(userModel);
    }
    public UserDTO registration (String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.insert(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDtoConverter.convert(userModel);
    }
}
