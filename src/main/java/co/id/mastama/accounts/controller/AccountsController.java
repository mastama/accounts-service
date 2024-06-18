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

    @GetMapping("/fetch")
    public ResponseEntity<ResponseDto> fetchAccountDetails(@RequestParam String mobileNumber) {
        log.info("Incoming Fetch account {}", mobileNumber);
        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        log.info("Outgoing Fetch account: {}", customerDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200, customerDto));
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@RequestBody CustomerDto customerDto) {
        log.info("Incoming Update account {}", customerDto);
        boolean isUpdated = iAccountsService.updateAccount(customerDto);
        if (isUpdated) {
            log.info("Outgoing Update account {}", customerDto);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200, customerDto));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500, customerDto));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails(@RequestParam String mobileNumber) {
        log.info("Incoming Delete account {}", mobileNumber);
        boolean isDeleted = iAccountsService.deleteAccount(mobileNumber);
        if (isDeleted) {
            log.info("Outgoing Delete account {}", mobileNumber);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200, null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500, null));
        }
    }
}
