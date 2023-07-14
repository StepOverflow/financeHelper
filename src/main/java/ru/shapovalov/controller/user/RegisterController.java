package ru.shapovalov.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.controller.Controller;
import ru.shapovalov.json.user.RegisterRequest;
import ru.shapovalov.json.user.RegisterResponse;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import java.util.Optional;

@Service("/register")
@RequiredArgsConstructor
public class RegisterController implements Controller<RegisterRequest, RegisterResponse> {
    private final UserAuthService userAuthService;

    @Override
    public RegisterResponse handle(RegisterRequest request) {
        Optional<UserDto> registeredUserOptional = Optional.ofNullable(userAuthService.registration(request.getEmail(), request.getPassword()));
        return registeredUserOptional.map(registeredUser -> new RegisterResponse(registeredUser.getId(), registeredUser.getEmail())).orElse(null);
    }

    @Override
    public Class<RegisterRequest> getRequestClass() {
        return RegisterRequest.class;
    }
}