package ru.shapovalov.controller.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.transaction.TransferRequest;
import ru.shapovalov.json.transaction.TransferResponse;
import ru.shapovalov.service.TransactionDto;
import ru.shapovalov.service.TransactionService;

@Controller("/transfer")
@RequiredArgsConstructor
public class MoneyTransferController implements SecureController<TransferRequest, TransferResponse> {

    private final TransactionService transactionService;

    @Override
    public TransferResponse handle(TransferRequest request, Integer userId) {
        TransactionDto transactionDto = transactionService.sendMoney(request.getSender(), request.getRecipient(), request.getSum(), userId, request.getCategoryIds());
        if (transactionDto != null) {
            return new TransferResponse(
                    transactionDto.getId(),
                    transactionDto.getSender(),
                    transactionDto.getRecipient(),
                    transactionDto.getSum(),
                    transactionDto.getCreatedDate()
            );
        }
        return null;
    }

    @Override
    public Class<TransferRequest> getRequestClass() {
        return TransferRequest.class;
    }
}