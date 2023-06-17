package ru.shapovalov.converter;

import org.springframework.stereotype.Service;
import ru.shapovalov.dao.TransactionModel;
import ru.shapovalov.service.TransactionDto;

@Service
public class TransactionModelToTransactionDtoConverter implements Converter<TransactionModel, TransactionDto> {
    @Override
    public TransactionDto convert(TransactionModel source) {
        TransactionDto transactionDto = new TransactionDto();
        transactionDto.setId(source.getId());
        transactionDto.setSender(source.getSender());
        transactionDto.setRecipient(source.getRecipient());
        transactionDto.setSum(source.getSum());
        transactionDto.setCreatedDate(source.getCreatedDate());
        return transactionDto;
    }
}