package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.Converter;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;
import ru.shapovalov.security.CustomUserDetails;
import ru.shapovalov.security.UserRole;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final Converter<User, UserDto> userDtoConverter;

    private final PasswordEncoder passwordEncoder;

    public Optional<UserDto> auth(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return Optional.of(userDtoConverter.convert(user));
            }
        }

        return Optional.empty();
    }

    public UserDto registration(String email, String password) {
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(UserRole.USER);
        String hash = passwordEncoder.encode(password);

        User user = new User();
        user.setEmail(email);
        user.setPassword(hash);
        user.setRoles(userRoles);

        user = userRepository.save(user);

        return userDtoConverter.convert(user);
    }

    public UserDto getByUserId(Long userId) {
        return userRepository.findById(userId).map(userDtoConverter::convert).orElse(null);
    }

    public UserDto currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof CustomUserDetails)) {
            return null;
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Optional<User> userOptional = userRepository.findById(customUserDetails.getId());

        if (userOptional.isEmpty()) {
            return null;
        }

        logger.info("customUserDetails user with email: {}", customUserDetails.getUsername());

        return userDtoConverter.convert(userOptional.get());
    }
}