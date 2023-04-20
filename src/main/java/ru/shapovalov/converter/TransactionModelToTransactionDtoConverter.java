package ru.shapovalov.converter;

import ru.shapovalov.dao.TransactionModel;
import ru.shapovalov.service.TransactionDto;

public class TransactionModelToTransactionDtoConverter implements Converter<TransactionModel, TransactionDto> {
    @Override
    public TransactionDto convert(TransactionModel source) {
        TransactionDto tDto = new TransactionDto();
        tDto.setId(source.getId());
        tDto.setSender(source.getSender());
        tDto.setRecipient(source.getRecipient());
        tDto.setSum(source.getSum());
        tDto.setTimestamp(source.getTimestamp());
        return tDto;
    }
}
