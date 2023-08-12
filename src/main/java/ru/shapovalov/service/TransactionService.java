package ru.shapovalov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.shapovalov.api.converter.TransactionModelToTransactionDtoConverter;
import ru.shapovalov.entity.Account;
import ru.shapovalov.entity.Category;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.entity.User;
import ru.shapovalov.exception.CustomException;
import ru.shapovalov.repository.AccountRepository;
import ru.shapovalov.repository.CategoryRepository;
import ru.shapovalov.repository.TransactionRepository;
import ru.shapovalov.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionModelToTransactionDtoConverter transactionDtoConverter;

    @Transactional
    public TransactionDto sendMoney(Long senderId, Long recipientId, int sum, Long userId, List<Long> categoryIds) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException("User not found"));

        Account sender = accountRepository.findByIdAndUserId(senderId, userId);
        if (sender == null) {
            throw new CustomException("Sender account not found or you don't have permission");
        }

        if (sender.getBalance() < sum) {
            throw new CustomException("Insufficient funds on the account");
        }

        Account recipient = null;
        if (recipientId != null) {
            recipient = accountRepository.findAccountById(recipientId);
        }

        Transaction transaction = new Transaction();
        transaction.setToAccount(recipient);
        transaction.setFromAccount(sender);
        transaction.setAmountPaid(sum);
        transaction.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        if (recipient != null) {
            recipient.setBalance(recipient.getBalance() + sum);
        }
        sender.setBalance(sender.getBalance() - sum);

        if (categoryIds != null && !categoryIds.isEmpty()) {
            List<Category> categories = categoryRepository.findAllById(categoryIds);
            transaction.setCategories(categories);
        }

        transaction = transactionRepository.save(transaction);
        return transactionDtoConverter.convert(transaction);
    }

    public List<TransactionDto> findByFromAccountIdInOrToAccountIdIn(List<Long> accountIds, List<Long> accountIds1) {
        return transactionRepository.findByFromAccountIdInOrToAccountIdIn(accountIds, accountIds1).stream()
                .map(transactionDtoConverter::convert).collect(Collectors.toList());
    }
}