package ru.shapovalov.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.json.user.UserRequest;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import javax.validation.Valid;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class RegisterUserApiController {
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid UserRequest request) {
        User existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser != null) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setPassword(md5Hex(request.getPassword()));

        User savedUser = userRepository.save(newUser);
        if (savedUser != null) {
            return ResponseEntity.ok("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user");
        }
    }
}