package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.converter.UserModelToUserDtoConverter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.dao.UserModel;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceTest {
    @InjectMocks
    UserAuthServiceImpl subj;
    @Mock
    UserDao userDao;
    @Mock
    DigestService digestService;
    @Mock
    UserModelToUserDtoConverter userDtoConverter;

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

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setEmail(email);
        userModel.setPassword(hashPassword);

        when(userDao.findByEmailAndHash(email, hashPassword)).thenReturn(userModel);

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail(email);
        when(userDtoConverter.convert(userModel)).thenReturn(userDto);

        Optional<UserDto> userDtoOptional = subj.auth(email, password);

        assertTrue(userDtoOptional.isPresent());
        assertEquals(Optional.of(userDto), userDtoOptional);

        verify(digestService, times(1)).hex(password);
        verify(userDao, times(1)).findByEmailAndHash(email, hashPassword);
        verify(userDtoConverter, times(1)).convert(userModel);
    }

    @Test
    public void registration_success() {
        String email = "test@example.com";
        String password = "password1";
        String hashedPassword = "hashedPassword1";

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setEmail(email);
        userModel.setPassword(hashedPassword);

        UserDto expectedUserDto = new UserDto();
        expectedUserDto.setId(userModel.getId());
        expectedUserDto.setEmail(userModel.getEmail());

        UserDao userDaoMock = mock(UserDao.class);
        DigestService digestServiceMock = mock(DigestService.class);
        UserModelToUserDtoConverter userDtoConverterMock = mock(UserModelToUserDtoConverter.class);

        UserAuthServiceImpl userAuthService = new UserAuthServiceImpl(userDaoMock, digestServiceMock, userDtoConverterMock);

        when(digestServiceMock.hex(password)).thenReturn(hashedPassword);
        when(userDaoMock.insert(email, hashedPassword)).thenReturn(userModel);
        when(userDtoConverterMock.convert(userModel)).thenReturn(expectedUserDto);

        UserDto actualUserDto = userAuthService.registration(email, password);

        assertEquals(expectedUserDto, actualUserDto);

        verify(digestServiceMock, times(1)).hex(password);
        verify(userDaoMock, times(1)).insert(email, hashedPassword);
        verify(userDtoConverterMock, times(1)).convert(userModel);
    }
}