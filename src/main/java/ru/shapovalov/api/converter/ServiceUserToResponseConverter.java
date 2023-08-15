package ru.shapovalov.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.shapovalov.api.json.user.AuthResponse;
import ru.shapovalov.service.UserDto;

@Component
public class ServiceUserToResponseConverter implements Converter<UserDto, AuthResponse> {
    @Override
    public AuthResponse convert(UserDto user) {
        return new AuthResponse(user.getId(), user.getEmail());
    }
}