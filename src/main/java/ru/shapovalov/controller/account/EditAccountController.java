package ru.shapovalov.controller.account;

import ru.shapovalov.json.account.EditAccountRequest;
import ru.shapovalov.json.account.EditAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.service.AccountService;

@Controller("/accounts/edit")
@RequiredArgsConstructor
public class EditAccountController implements SecureController<EditAccountRequest, EditAccountResponse> {
    private final AccountService accountService;

    @Override
    public EditAccountResponse handle(EditAccountRequest request, Integer userId) {
        return new EditAccountResponse(accountService.edit(request.getId(), request.getName(), userId));
    }

    @Override
    public Class<EditAccountRequest> getRequestClass() {
        return EditAccountRequest.class;
    }
}