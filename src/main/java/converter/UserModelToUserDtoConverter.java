package converter;

import dao.UserModel;
import service.UserDto;

public class UserModelToUserDtoConverter<S, T> implements Converter<UserModel, UserDto> {
    @Override
    public UserDto convert(UserModel source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}
