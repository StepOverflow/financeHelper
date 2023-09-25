package ru.shapovalov.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.shapovalov.api.json.transaction.ReportRequest;
import ru.shapovalov.api.json.transaction.TransferRequest;
import ru.shapovalov.service.*;

import static java.util.List.of;
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

    @Autowired
    ObjectMapper om;

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
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setDays(30);

        mockMvc.perform(post("/api/transactions/user/income")
                        .contentType("application/json")
                        .content(om.writeValueAsString(reportRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    public void testGetUserExpenseInPeriod() throws Exception {
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setDays(30);

        mockMvc.perform(post("/api/transactions/user/expense")
                        .contentType("application/json")
                        .content(om.writeValueAsString(reportRequest))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    public void testTransfer() throws Exception {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setFromAccountId(1L);
        transferRequest.setToAccountId(2L);
        transferRequest.setSum(100);
        transferRequest.setCategoryIds(of(1L, 2L));

        when(transactionService.sendMoney(1L, 2L, 100, 1L, of(1L, 2L))).thenReturn(new TransactionDto());

        mockMvc.perform(post("/api/transactions/transfer")
                        .contentType("application/json")
                        .content(om.writeValueAsString(transferRequest))
                )
                .andExpect(status().isOk())
                .andExpect(content().string("Transfer successfully"));
    }
}