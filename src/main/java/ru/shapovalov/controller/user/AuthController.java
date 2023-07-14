package ru.shapovalov.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.controller.Controller;
import ru.shapovalov.json.user.AuthRequest;
import ru.shapovalov.json.user.AuthResponse;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import java.util.Optional;

@Service("/login")
@RequiredArgsConstructor
public class AuthController implements Controller<AuthRequest, AuthResponse> {
    private final UserAuthService authService;

    @Override
    public AuthResponse handle(AuthRequest request) {
        Optional<UserDto> userOptional = authService.auth(request.getEmail(), request.getPassword());
        return userOptional.map(userDto -> new AuthResponse(userDto.getId(), userDto.getEmail())).orElse(null);
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }
}