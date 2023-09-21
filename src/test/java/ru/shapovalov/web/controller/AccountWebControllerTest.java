package ru.shapovalov.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
public class AccountWebControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    AccountService accountService;

    @MockBean
    UserService userService;

    @WithMockUser
    @Test
    public void listAccounts() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");
        List<AccountDto> accounts = new ArrayList<>();

        when(userService.currentUser()).thenReturn(userDto);
        when(accountService.getAll(userDto.getId())).thenReturn(accounts);

        mockMvc.perform(get("/accounts/list"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("accounts", accounts))
                .andExpect(view().name("account-list"));
    }

    @WithMockUser
    @Test
    public void getCreateForm() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(get("/accounts/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-create"));
    }

    @WithMockUser
    @Test
    public void testPostCreateAccount() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(post("/accounts/create")
                        .param("name", "AccountName"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/accounts/list"));

        verify(accountService, times(1)).create("AccountName", userDto.getId());
    }
}