package ru.shapovalov.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;
import ru.shapovalov.api.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.entity.User;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.TransactionRepository;
import ru.shapovalov.repository.UserRepository;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private TransactionModelToTransactionDtoConverter transactionDtoConverter;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    public void testSendMoney() {
        Long senderId = 1L;
        Long recipientId = 2L;
        int sum = 100;
        Long userId = 3L;
        List<Long> categoryIds = Arrays.asList(4L, 5L);

        User user = new User();
        user.setId(userId);

        Account sender = new Account();
        sender.setId(senderId);
        sender.setBalance(200);
        sender.setUser(user);

        Account recipient = new Account();
        recipient.setId(recipientId);
        recipient.setBalance(150);
        recipient.setUser(user);

        Category category1 = new Category();
        category1.setId(4L);
        Category category2 = new Category();
        category2.setId(5L);
        List<Category> categories = Arrays.asList(category1, category2);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(sender);
        transaction.setToAccount(recipient);
        transaction.setAmountPaid(sum);
        transaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(accountRepository.findByIdAndUserId(senderId, userId)).thenReturn(sender);
        when(accountRepository.findAccountById(recipientId)).thenReturn(recipient);
        when(categoryRepository.findAllById(categoryIds)).thenReturn(categories);
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(transactionDtoConverter.convert(any(Transaction.class))).thenReturn(new TransactionDto());

        TransactionDto transactionDto = transactionService.sendMoney(senderId, recipientId, sum, userId, categoryIds);

        assertNotNull(transactionDto);
        assertEquals(100, sender.getBalance());
        assertEquals(250, recipient.getBalance());
        verify(userRepository, times(1)).findById(userId);
        verify(accountRepository, times(1)).findByIdAndUserId(senderId, userId);
        verify(accountRepository, times(1)).findAccountById(recipientId);
        verify(categoryRepository, times(1)).findAllById(categoryIds);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
        verify(transactionDtoConverter, times(1)).convert(any(Transaction.class));
    }
}