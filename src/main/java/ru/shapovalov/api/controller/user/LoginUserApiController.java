package ru.shapovalov.api.controller.user;

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
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class LoginUserApiController {
    private final UserRepository userRepository;
    private final ServiceUserToResponseConverter converter;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid UserRequest request,
                                              HttpServletRequest httpServletRequest) {
        User user = userRepository.findByEmailAndPassword(
                request.getEmail(),
                md5Hex(request.getPassword())
        );
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("userId", user.getId());

        return ResponseEntity.ok(converter.convert(user));
    }
}