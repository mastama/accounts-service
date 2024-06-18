package co.id.mastama.accounts.controller;

import co.id.mastama.accounts.constants.AccountsConstants;
import co.id.mastama.accounts.dto.CustomerDto;
import co.id.mastama.accounts.dto.ResponseDto;
import co.id.mastama.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AccountsController {

    private final IAccountsService iAccountsService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody CustomerDto customerDto) {
        log.info("Incoming Create account {}", customerDto);
        iAccountsService.createAccount(customerDto);
        log.info("Outgoing Create account: {}", customerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201, AccountsConstants.MESSAGE_201, customerDto));
    }
}
