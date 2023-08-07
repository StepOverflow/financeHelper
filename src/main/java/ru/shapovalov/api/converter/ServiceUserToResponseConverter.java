package ru.shapovalov.api.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.shapovalov.api.json.user.AuthResponse;
import ru.shapovalov.entity.User;

@Component
public class ServiceUserToResponseConverter implements Converter<User, AuthResponse> {
    @Override
    public AuthResponse convert(User user) {
        return new AuthResponse(user.getId(), user.getEmail());
    }
}