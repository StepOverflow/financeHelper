package ru.shapovalov.controller.user;

import ru.shapovalov.controller.Controller;
import ru.shapovalov.json.user.AuthRequest;
import ru.shapovalov.json.user.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.service.UserAuthService;

@Service("/login")
@RequiredArgsConstructor
public class AuthController implements Controller<AuthRequest, AuthResponse> {
    private final UserAuthService authService;

    @Override
    public AuthResponse handle(AuthRequest request) {
        return authService.auth(request.getEmail(), request.getPassword()).map(userDto -> new AuthResponse(userDto.getId(), userDto.getEmail())).orElse(null);
    }

    @Override
    public Class<AuthRequest> getRequestClass() {
        return AuthRequest.class;
    }
}