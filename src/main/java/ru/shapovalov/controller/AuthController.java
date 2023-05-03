package ru.shapovalov.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.json.AuthRequest;
import ru.shapovalov.json.AuthResponse;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import java.util.Optional;

@Service("login")
@RequiredArgsConstructor
public class AuthController implements Controller<AuthRequest, AuthResponse> {
    private final UserAuthService authService;

    @Override
    public AuthResponse handle(AuthRequest request) {
        Optional<UserDto> userOptional = authService.auth(request.getEmail(), request.getPassword());
        if (userOptional.isPresent()) {
            UserDto userDto = userOptional.get();
            return new AuthResponse(userDto.getId(), userDto.getEmail());
        }
        return null;
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }
}

