package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.api.converter.UserToUserDtoConverter;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.UserRepository;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private DigestService digestService;

    @Mock
    private UserToUserDtoConverter userDtoConverter;

    @InjectMocks
    private UserService userService;

    @Test
    public void testAuth_Successful() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail(email);

        when(digestService.hex(password)).thenReturn(hashedPassword);
        when(userRepository.findByEmailAndPassword(email, hashedPassword)).thenReturn(Optional.of(user));
        when(userDtoConverter.convert(user)).thenReturn(userDto);

        Optional<UserDto> authResult = userService.auth(email, password);

        assertTrue(authResult.isPresent());
        assertEquals(userDto, authResult.get());

        verify(digestService, times(1)).hex(password);
        verify(userRepository, times(1)).findByEmailAndPassword(email, hashedPassword);
        verify(userDtoConverter, times(1)).convert(user);
    }

    @Test
    public void testAuth_Failure() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = "hashedPassword";

        when(digestService.hex(password)).thenReturn(hashedPassword);
        when(userRepository.findByEmailAndPassword(email, hashedPassword)).thenReturn(Optional.empty());

        Optional<UserDto> authResult = userService.auth(email, password);

        assertFalse(authResult.isPresent());

        verify(digestService, times(1)).hex(password);
        verify(userRepository, times(1)).findByEmailAndPassword(email, hashedPassword);
        verify(userDtoConverter, never()).convert(any(User.class));
    }

    @Test
    public void testRegistration() {
        String email = "test@example.com";
        String password = "password";
        String hashedPassword = "hashedPassword";

        User user = new User();
        user.setId(1L);
        user.setEmail(email);
        user.setPassword(hashedPassword);

        UserDto userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail(email);

        when(digestService.hex(password)).thenReturn(hashedPassword);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userDtoConverter.convert(user)).thenReturn(userDto);

        UserDto registrationResult = userService.registration(email, password);

        assertNotNull(registrationResult);
        assertEquals(userDto, registrationResult);

        verify(digestService, times(1)).hex(password);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userDtoConverter, times(1)).convert(user);
    }
}