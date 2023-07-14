package ru.shapovalov.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.transaction.TransferRequest;
import ru.shapovalov.json.transaction.TransferResponse;
import ru.shapovalov.service.TransactionDto;
import ru.shapovalov.service.TransactionService;

import java.util.Optional;

@Controller("/transfer")
@RequiredArgsConstructor
public class MoneyTransferController implements SecureController<TransferRequest, TransferResponse> {

    private final TransactionService transactionService;

    @Override
    public TransferResponse handle(TransferRequest request, Integer userId) {
        Optional<TransactionDto> transactionOptional = Optional.ofNullable(transactionService.sendMoney(request.getSender(), request.getRecipient(), request.getSum(), userId, request.getCategoryIds()));
        return transactionOptional.map(transactionDto ->
                new TransferResponse(
                        transactionDto.getId(),
                        transactionDto.getSender(),
                        transactionDto.getRecipient(),
                        transactionDto.getSum(),
                        transactionDto.getCreatedDate()
                )).orElse(null);
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}