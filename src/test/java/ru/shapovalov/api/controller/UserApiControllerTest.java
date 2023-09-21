package ru.shapovalov.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.api.converter.ServiceUserToResponseConverter;
import ru.shapovalov.repository.UserRepository;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserApiController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class UserApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @SpyBean
    ServiceUserToResponseConverter converter;

    @Before
    public void setUp() {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);
    }

    @Test
    public void register() throws Exception {
        when(userService.registration("new@example.com", "password"))
                .thenReturn(
                        new UserDto()
                                .setId(1L)
                                .setEmail("new@example.com"));

        mockMvc.perform(post("/api/register")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"email\": \"new@example.com\",\n" +
                                "  \"password\": \"password\"\n" +
                                "}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("email").value("new@example.com"));
    }

    @WithUserDetails(value = "user1@example.com", userDetailsServiceBeanName = "userDetailsService")
    @Test
    public void getUserInfo() throws Exception {
        mockMvc.perform(get("/api/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("email").value("user1@example.com"));
    }
}