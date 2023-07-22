package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.UserToUserDtoConverter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.entity.User;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceTest {
    @InjectMocks
    UserAuthService subj;

    @Mock
    UserDao userDao;

    @Mock
    DigestService digestService;

    @Mock
    UserToUserDtoConverter userDtoConverter;

    @Test
    public void auth_userNotFound() {
        String email = "user1@example.com";
        String password = "password1";
        String hashPassword = "hex";

        when(digestService.hex(password)).thenReturn(hashPassword);
        when(userDao.findByEmailAndHash(email, hashPassword)).thenReturn(null);

        Optional<UserDto> userDtoOptional = subj.auth(email, password);

        assertFalse(userDtoOptional.isPresent());

        verify(digestService, times(1)).hex(password);
        verify(userDao, times(1)).findByEmailAndHash(email, hashPassword);
        verifyZeroInteractions(userDtoConverter);

    }

    @Test
    public void auth_userFound() {
        String email = "user1@example.com";
        String password = "password1";
        String hashPassword = "hex";

        when(digestService.hex(password)).thenReturn(hashPassword);

        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setPassword(hashPassword);

        when(userDao.findByEmailAndHash(email, hashPassword)).thenReturn(user);

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail(email);
        when(userDtoConverter.convert(user)).thenReturn(userDto);

        Optional<UserDto> userDtoOptional = subj.auth(email, password);

        assertTrue(userDtoOptional.isPresent());
        assertEquals(userDto, userDtoOptional.get());

        verify(digestService, times(1)).hex(password);
        verify(userDao, times(1)).findByEmailAndHash(email, hashPassword);
        verify(userDtoConverter, times(1)).convert(user);
    }

    @Test
    public void registration_success() {
        String email = "test@example.com";
        String password = "password1";
        String hashPassword = "hashPassword1";

        User user = new User();
        user.setId(1);
        user.setEmail(email);
        user.setPassword(hashPassword);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(user.getId());
        expectedUserDto.setEmail(user.getEmail());

        UserDao userDaoMock = mock(UserDao.class);
        DigestService digestServiceMock = mock(DigestService.class);
        UserToUserDtoConverter userDtoConverterMock = mock(UserToUserDtoConverter.class);

        UserAuthService userAuthService = new UserAuthService(userDaoMock, digestServiceMock, userDtoConverterMock);

        when(digestServiceMock.hex(password)).thenReturn(hashPassword);
        when(userDaoMock.insert(email, hashPassword)).thenReturn(user);
        when(userDtoConverterMock.convert(user)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userAuthService.registration(email, password);

        assertEquals(expectedUserDto, actualUserDto);

        verify(digestServiceMock, times(1)).hex(password);
        verify(userDaoMock, times(1)).insert(email, hashPassword);
        verify(userDtoConverterMock, times(1)).convert(user);
    }
}