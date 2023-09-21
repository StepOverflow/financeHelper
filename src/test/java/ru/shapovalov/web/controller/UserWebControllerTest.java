package ru.shapovalov.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
public class UserWebControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Test
    public void getLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login-form"));
    }

    @WithMockUser
    @Test
    public void index() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(get("/personal-area"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("id", 1L))
                .andExpect(model().attribute("email", "user1@example.com"))
                .andExpect(model().attribute("userDto", userDto))
                .andExpect(view().name("personal-area-form"));
    }

    @WithMockUser
    @Test
    public void redirectToPersonalArea_Authorized() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/personal-area"));
    }

    @WithAnonymousUser
    @Test
    public void redirectToPersonalArea_Unauthorized() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    public void getRegistration() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register-form"));

    }

    @Test
    public void postRegistration_Success() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.registration("user1@example.com", "password")).thenReturn(userDto);

        mockMvc.perform(post("/register")
                        .param("email", "user1@example.com")
                        .param("password", "password"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("userDto", userDto))
                .andExpect(view().name("/register-success"));
    }
}