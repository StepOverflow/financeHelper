package ru.shapovalov.converter;

import ru.shapovalov.dao.UserModel;
import ru.shapovalov.service.UserDto;
public class UserModelToUserDtoConverter implements Converter<UserModel, UserDto> {
    @Override
    public UserDto convert(UserModel source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}