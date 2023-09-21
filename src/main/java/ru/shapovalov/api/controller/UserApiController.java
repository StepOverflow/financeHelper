package ru.shapovalov.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.shapovalov.api.converter.ServiceUserToResponseConverter;
import ru.shapovalov.api.json.user.AuthResponse;
import ru.shapovalov.api.json.user.UserRequest;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

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

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserRequest request) {
        UserDto userDto = userService.registration(request.getEmail(), request.getPassword());
        if (userDto != null) {
            return ok(converter.convert(userDto));
        } else {
            return status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserRequest request) {
        Optional<UserDto> userDto = userService.auth(request.getEmail(), request.getPassword());
        return userDto.map(dto -> ok(converter.convert(dto))).orElseGet(() -> status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }

    @GetMapping("/info")
    public ResponseEntity<AuthResponse> getUserInfo() {
        UserDto userDto = userService.currentUser();
        if (userDto == null) {
            return status(HttpStatus.UNAUTHORIZED).build();
        }
        return ok(converter.convert(userDto));
    }
}