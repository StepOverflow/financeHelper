package service;

import ru.shapovalov.converter.UserModelToUserDtoConverter;
import ru.shapovalov.dao.UserDao;
import ru.shapovalov.dao.UserModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import ru.shapovalov.service.DigestService;
import ru.shapovalov.service.UserAuthService;
import ru.shapovalov.service.UserDto;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
@RunWith(MockitoJUnitRunner.class)
public class UserAuthServiceTest {
    UserAuthService subj;

    UserDao userDao;
    DigestService digestService;
    UserModelToUserDtoConverter userDtoConverter;

    @Before
    public void setUp() throws Exception {
        userDao = mock(UserDao.class);
        digestService = mock(DigestService.class);
        userDtoConverter = mock(UserModelToUserDtoConverter.class);

        subj = new UserAuthService(userDao, digestService, userDtoConverter);
    }

    @Test
    public void auth_userNotFound() {
        when(digestService.hex("password1")).thenReturn("hex");
        when(userDao.findByEmailAndHash("user1@example.com", "hex")).thenReturn(null);

        UserDto user = subj.auth("user1@example.com", "password1");

        assertNull(user);

        verify(digestService, times(1)).hex("password1");
        verify(userDao, times(1)).findByEmailAndHash("user1@example.com", "hex");
        verifyZeroInteractions(userDtoConverter);
    }

    @Test
    public void auth_userFound() {
        when(digestService.hex("password1")).thenReturn("hex");

        UserModel userModel = new UserModel();
        userModel.setId(1);
        userModel.setEmail("user1@example.com");
        userModel.setPassword("hex");
        when(userDao.findByEmailAndHash("user1@example.com", "hex")).thenReturn(userModel);

        UserDto userDto = new UserDto();
        userDto.setId(1);
        userDto.setEmail("user1@example.com");
        when(userDtoConverter.convert(userModel)).thenReturn(userDto);

        UserDto user = subj.auth("user1@example.com", "password1");

        assertNotNull(user);
        assertEquals(userDto, user);

        verify(digestService, times(1)).hex("password1");
        verify(userDao, times(1)).findByEmailAndHash("user1@example.com", "hex");
        verify(userDtoConverter, times(1)).convert(userModel);
    }

    @Test
    public void registration() {

    }
}