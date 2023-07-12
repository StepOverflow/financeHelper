package ru.shapovalov.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.controller.Controller;
import ru.shapovalov.json.user.RegisterRequest;
import ru.shapovalov.json.user.RegisterResponse;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

@Service("/register")
@RequiredArgsConstructor
public class RegisterController implements Controller<RegisterRequest, RegisterResponse> {
    private final UserAuthService userAuthService;

    @Override
    public RegisterResponse handle(RegisterRequest request) {
        UserDto registeredUser = userAuthService.registration(request.getEmail(), request.getPassword());

        if (registeredUser != null) {
            return new RegisterResponse(registeredUser.getId(), registeredUser.getEmail());
        } else {
            return null;
        }
    }

    @Override
    public Class<RegisterRequest> getRequestClass() {
        return RegisterRequest.class;
    }
}