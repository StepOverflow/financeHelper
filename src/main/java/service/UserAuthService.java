package service;

import converter.UserModelToUserDtoConverter;
import dao.UserDao;
import dao.UserModel;

public class UserAuthService {
    private final UserDao userDao;
    private final DigestService digestService;
    private final UserModelToUserDtoConverter userDtoConverter;

    public UserAuthService() {
        this.userDtoConverter = new UserModelToUserDtoConverter();
        this.digestService = new Md5DigestService();
        this.userDao = new UserDao();

    }

    public UserDto auth(String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.findByEmailAndHash(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDtoConverter.convert(userModel);
    }
    public UserDto registration (String email, String password) {
        String hash = digestService.hex(password);

        UserModel userModel = userDao.insert(email, hash);
        if (userModel == null) {
            return null;
        }
        return userDtoConverter.convert(userModel);
    }
}
