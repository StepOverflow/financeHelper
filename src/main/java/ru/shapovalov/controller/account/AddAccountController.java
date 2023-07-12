package ru.shapovalov.controller.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.account.AddAccountRequest;
import ru.shapovalov.json.account.AddAccountResponse;
import ru.shapovalov.service.AccountDto;
import ru.shapovalov.service.AccountService;

@Controller("/accounts/add")
@RequiredArgsConstructor
public class AddAccountController implements SecureController<AddAccountRequest, AddAccountResponse> {
    private final AccountService accountService;

    @Override
    public AddAccountResponse handle(AddAccountRequest request, Integer userId) {
        AccountDto accountDto = accountService.create(request.getName(), userId);
        if (accountDto != null) {
            return new AddAccountResponse(
                    accountDto.getId(),
                    accountDto.getAccountName(),
                    accountDto.getBalance()
            );
        }
        return null;
    }

    @Override
    public Class<AddAccountRequest> getRequestClass() {
        return AddAccountRequest.class;
    }
}