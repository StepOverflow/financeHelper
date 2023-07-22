package ru.shapovalov.converter;

import org.springframework.stereotype.Service;
import ru.shapovalov.entity.Transaction;
import ru.shapovalov.service.TransactionDto;

@Service
public class TransactionModelToTransactionDtoConverter implements Converter<Transaction, TransactionDto> {
    @Override
    public TransactionDto convert(Transaction source) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(source.getId());
        transactionDto.setSender(source.getFromAccount().getId());
        transactionDto.setRecipient(source.getToAccount().getId());
        transactionDto.setSum(source.getAmountPaid());
        transactionDto.setCreatedDate(source.getCreatedDate());
        return transactionDto;
    }
}