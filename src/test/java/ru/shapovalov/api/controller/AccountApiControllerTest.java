package ru.shapovalov.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;
import ru.shapovalov.service.UserDto;
import ru.shapovalov.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountApiController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class AccountApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @MockBean
    AccountService accountService;

    @Before
    public void setUp() {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);
    }

    @Test
    public void testGetAllByUserId() throws Exception {
        List<AccountDto> mockAccounts = Arrays.asList(
                new AccountDto().setId(1L).setAccountName("Account1").setBalance(100),
                new AccountDto().setId(2L).setAccountName("Account2").setBalance(200)
        );
        when(accountService.findAllByUserId(1L)).thenReturn(mockAccounts);

        mockMvc.perform(get("/api/accounts/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("length()").value(2))
                .andExpect(jsonPath("[0].id").value(1))
                .andExpect(jsonPath("[0].accountName").value("Account1"))
                .andExpect(jsonPath("[0].balance").value(100));
    }

    @Test
    public void testCreate() throws Exception {
        AccountDto mockAccountDto = new AccountDto().setId(1L).setAccountName("NewAccount").setBalance(0);
        when(accountService.create(Mockito.anyString(), eq(1L))).thenReturn(mockAccountDto);

        mockMvc.perform(post("/api/accounts/create")
                        .contentType("application/json")
                        .content("{\"name\": \"NewAccount\"}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(1))
                .andExpect(jsonPath("accountName").value("NewAccount"))
                .andExpect(jsonPath("balance").value(0.0));
    }

    @Test
    public void testDeleteAccount() throws Exception {
        when(accountService.delete(eq(1L), eq(1L))).thenReturn(true);

        mockMvc.perform(delete("/api/accounts/delete")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"accountId\": \"1\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("deleted").value(true));

    }

    @Test
    public void testEditAccount() throws Exception {
        when(accountService.edit(eq(1L), eq("NewName"), eq(1L))).thenReturn(true);

        mockMvc.perform(post("/api/accounts/edit")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"id\": \"1\",\n" +
                                "  \"name\" : \"NewName\"\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.accountName").value("NewName"));
    }
}