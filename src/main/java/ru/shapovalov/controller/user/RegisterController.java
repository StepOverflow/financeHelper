package ru.shapovalov.controller.user;

import ru.shapovalov.controller.Controller;
import ru.shapovalov.json.user.RegisterRequest;
import ru.shapovalov.json.user.RegisterResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.service.UserAuthService;

import java.util.Optional;

@Service("/register")
@RequiredArgsConstructor
public class RegisterController implements Controller<RegisterRequest, RegisterResponse> {
    private final UserAuthService userAuthService;

    @Override
    public RegisterResponse handle(RegisterRequest request) {
        return Optional.ofNullable(userAuthService.registration(request.getEmail(), request.getPassword())).map(registeredUser -> new RegisterResponse(registeredUser.getId(), registeredUser.getEmail())).orElse(null);
    }

    @Override
    public Class<RegisterRequest> getRequestClass() {
        return RegisterRequest.class;
    }
}