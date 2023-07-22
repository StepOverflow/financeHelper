package ru.shapovalov.controller.account;

import ru.shapovalov.controller.SecureController;
import ru.shapovalov.json.account.AddAccountRequest;
import ru.shapovalov.json.account.AddAccountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import ru.shapovalov.service.AccountService;

import java.util.Optional;

@Controller("/accounts/add")
@RequiredArgsConstructor
public class AddAccountController implements SecureController<AddAccountRequest, AddAccountResponse> {
    private final AccountService accountService;

    @Override
    public AddAccountResponse handle(AddAccountRequest request, Integer userId) {
        return Optional.ofNullable(accountService.create(request.getName(), userId)).map(accountDto -> new AddAccountResponse(
                accountDto.getId(),
                accountDto.getAccountName(),
                accountDto.getBalance()
        )).orElse(null);
    }

    @Override
    public Class<AddAccountRequest> getRequestClass() {
        return AddAccountRequest.class;
    }
}