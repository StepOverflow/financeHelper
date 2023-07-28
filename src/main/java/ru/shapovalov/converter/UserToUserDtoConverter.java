package ru.shapovalov.converter;

import ru.shapovalov.entity.User;
import org.springframework.stereotype.Service;
import ru.shapovalov.service.UserDto;

@Service
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        UserDto userDto = new UserDto();
        userDto.setId(source.getId());
        userDto.setEmail(source.getEmail());
        return userDto;
    }
}