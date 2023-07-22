package ru.shapovalov.controller.account;

import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.account.DeleteAccountRequest;
import ru.shapovalov.json.account.DeleteAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.service.AccountService;

@Controller("/accounts/delete")
@RequiredArgsConstructor
public class DeleteAccountController implements SecureController<DeleteAccountRequest, DeleteAccountResponse> {
    private final AccountService accountService;

    @Override
    public DeleteAccountResponse handle(DeleteAccountRequest request, Integer userId) {
        return new DeleteAccountResponse(accountService.delete(request.getId(), userId));
    }

    @Override
    public Class<DeleteAccountRequest> getRequestClass() {
        return DeleteAccountRequest.class;
    }
}