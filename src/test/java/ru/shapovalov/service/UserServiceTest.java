package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shapovalov.api.converter.Converter;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private Converter<User, UserDto> userDtoConverter;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAuth_SuccessAuthentication() {
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = "HashedPassword";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);
        when(userDtoConverter.convert(any(User.class))).thenReturn(new UserDto());

        Optional<UserDto> result = userService.auth(email, password);

        assertTrue(result.isPresent());
    }

    @Test
    public void testAuth_FailedAuthentication_WrongPassword() {
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = "wrongHashedPassword";
        User user = new User();
        user.setEmail(email);
        user.setPassword(hashedPassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(false);

        Optional<UserDto> result = userService.auth(email, password);

        assertFalse(result.isPresent());
    }

    @Test
    public void testAuth_FailedAuthentication_UserNotFound() {
        String email = "nonexistent@example.com";
        String password = "password123";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<UserDto> result = userService.auth(email, password);

        assertFalse(result.isPresent());
    }
}