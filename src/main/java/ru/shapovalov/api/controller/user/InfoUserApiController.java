package ru.shapovalov.api.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.shapovalov.api.converter.ServiceUserToResponseConverter;
import ru.shapovalov.api.json.user.AuthResponse;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class InfoUserApiController {
    private final UserRepository userRepository;
    private final ServiceUserToResponseConverter converter;

    @PostMapping("/info")
    public ResponseEntity<AuthResponse> getUserInfo(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(converter.convert(user));
    }
}