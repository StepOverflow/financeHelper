package ru.shapovalov.controller.transaction;

import ru.shapovalov.json.transaction.TransferRequest;
import ru.shapovalov.json.transaction.TransferResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.service.TransactionService;

import java.util.Optional;

@Controller("/transfer")
@RequiredArgsConstructor
public class MoneyTransferController implements SecureController<TransferRequest, TransferResponse> {

    private final TransactionService transactionService;

    @Override
    public TransferResponse handle(TransferRequest request, Integer userId) {
        return Optional.ofNullable(
                        transactionService.sendMoney(
                                request.getSender(),
                                request.getRecipient(),
                                request.getSum(),
                                userId, request.
                                        getCategoryIds()))
                .map(transactionDto ->
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