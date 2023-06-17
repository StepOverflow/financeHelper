package ru.shapovalov.converter;

import org.springframework.stereotype.Service;
import ru.shapovalov.dao.UserModel;
import ru.shapovalov.service.UserDto;

@Service
public class UserModelToUserDtoConverter implements Converter<UserModel, UserDto> {
    @Override
    public UserDto convert(UserModel source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}