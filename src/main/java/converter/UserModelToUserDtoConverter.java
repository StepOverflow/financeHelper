package converter;

import dao.UserModel;
import service.UserDTO;

public class UserModelToUserDtoConverter<S, T> implements Converter<UserModel, UserDTO> {
    @Override
    public UserDTO convert(UserModel source) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(source.getId());
        userDTO.setEmail(source.getEmail());
        return userDTO;
    }
}
