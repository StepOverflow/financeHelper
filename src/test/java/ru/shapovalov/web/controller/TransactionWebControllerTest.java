package ru.shapovalov.web.controller;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionWebController.class)
@Import({SecurityConfiguration.class, MockSecurityConfiguration.class})
public class TransactionWebControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    TransactionService transactionService;
    @MockBean
    CategoryService categoryService;
    @MockBean
    UserService userService;

    @Test
    public void getTransfer() throws Exception {
        UserDto userDto = new UserDto()
                .setId(1L)
                .setEmail("user1@example.com");

        when(userService.currentUser()).thenReturn(userDto);

        mockMvc.perform(get("/transactions/transfer"))
                .andExpect(status().isOk())
                .andExpect(view().name("transaction-transfer"));
    }

    @Test
    public void postTransfer() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);

        TransactionDto transactionDto = new TransactionDto();

        when(userService.currentUser()).thenReturn(userDto);
        when(transactionService.sendMoney(1L, 2L, 100, 1L, List.of(1L, 2L))).thenReturn(transactionDto);

        mockMvc.perform(post("/transactions/transfer")
                        .param("fromAccountId", "1")
                        .param("toAccountId", "2")
                        .param("sum", "100")
                        .param("categoryIds", "1", "2"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("transaction"))
                .andExpect(view().name("transaction-receipt"));

        verify(transactionService, times(1)).sendMoney(1L, 2L, 100, 1L, List.of(1L, 2L));
    }

    @Test
    public void expenseReport() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setId(1L);
        Map<String, Long> expenses = new HashMap<>();
        when(userService.currentUser()).thenReturn(userDto);
        when(categoryService.getResultExpenseInPeriodByCategory(1L, 7)).thenReturn(expenses);

        mockMvc.perform(post("/transactions/expense")
                        .param("days", "7"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("report"))
                .andExpect(view().name("transaction-expense"));

    }
}