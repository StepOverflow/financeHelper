package ru.shapovalov.api.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.shapovalov.MockSecurityConfiguration;
import ru.shapovalov.SecurityConfiguration;
import ru.shapovalov.service.*;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionApiController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
@RunWith(SpringRunner.class)
public class TransactionApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @MockBean
    TransactionService transactionService;
    @MockBean
    AccountService accountService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    UserService userService;

    @Before
    public void setUp() {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);
    }

    @Test
    public void testGetUserTransactions() throws Exception {
        mockMvc.perform(post("/api/transactions/list"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetUserIncomeInPeriod() throws Exception {
        mockMvc.perform(post("/api/transactions/user/income")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"days\": 30\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    public void testGetUserExpenseInPeriod() throws Exception {
        mockMvc.perform(post("/api/transactions/user/expense")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"days\": 30\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    public void testTransfer() throws Exception {
        when(transactionService.sendMoney(1L, 2L, 100, 1L, List.of(1L, 2L))).thenReturn(new TransactionDto());

        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType("application/json")
                        .content("{\n" +
                                "  \"fromAccountId\": 1,\n" +
                                "  \"toAccountId\": 2,\n" +
                                "  \"sum\": 100,\n" +
                                "  \"categoryIds\": [\n" +
                                "    1,\n" +
                                "    2\n" +
                                "  ]\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successfully"));
    }
}