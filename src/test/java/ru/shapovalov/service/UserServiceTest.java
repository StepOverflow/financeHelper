package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.shapovalov.api.converter.Converter;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;
import ru.shapovalov.security.UserRole;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testAuth_SuccessfulAuthentication() {
        String email = "test@example.com";
        String password = "password123";
        String hashedPassword = "hashedPassword123";
        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);
        Set<UserRole> roles = new HashSet<>();
        roles.add(UserRole.USER);
        user.setRoles(roles);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, hashedPassword)).thenReturn(true);

        Optional<UserDto> result = userService.auth(email, password);

        assertTrue(result.isPresent());
        UserDto userDto = result.get();
        assertEquals(user.getId(), userDto.getId());
        assertEquals(user.getEmail(), userDto.getEmail());
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