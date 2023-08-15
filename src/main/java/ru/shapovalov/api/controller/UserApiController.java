package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.converter.ServiceUserToResponseConverter;
import ru.shapovalov.api.json.user.AuthResponse;
import ru.shapovalov.api.json.user.UserRequest;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserApiController {
    private final UserService userService;
    private final ServiceUserToResponseConverter converter;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserRequest request, HttpServletRequest httpServletRequest) {
        Optional<UserDto> user = userService.auth(request.getEmail(), request.getPassword());
        if (user.isEmpty()) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = httpServletRequest.getSession();
        UserDto userDto = user.get();
        session.setAttribute("userId", userDto.getId());

        return ok(converter.convert(userDto));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest request) {
        UserDto userDto = userService.registration(request.getEmail(), request.getPassword());
        if (userDto != null) {
            return ok(converter.convert(userDto));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            return ok("Logged out successfully");
        } else {
            return status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
    }

    @PostMapping("/info")
    public ResponseEntity<AuthResponse> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        UserDto userDto = userService.getByUserId(userId);
        if (userDto == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }

        return ok(converter.convert(userDto));
    }
}