package co.id.mastama.accounts.service.impl;

import co.id.mastama.accounts.constants.AccountsConstants;
import co.id.mastama.accounts.dto.AccountsDto;
import co.id.mastama.accounts.dto.CustomerDto;
import co.id.mastama.accounts.entity.Accounts;
import co.id.mastama.accounts.entity.Customer;
import co.id.mastama.accounts.exception.CustomerAlreadyExistsException;
import co.id.mastama.accounts.exception.ResourceNotFoundException;
import co.id.mastama.accounts.mapper.AccountsMapper;
import co.id.mastama.accounts.mapper.CustomerMapper;
import co.id.mastama.accounts.repository.AccountsRepository;
import co.id.mastama.accounts.repository.CustomerRepository;
import co.id.mastama.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Slf4j
public class AccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        log.info("Start Creating account {}", customerDto.getName());
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        // check if customer is existing
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNUmber " + "- " + customer.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);

        // save customer to accounts
        accountsRepository.save(createNewAccount(savedCustomer));
    }

    public Accounts createNewAccount(Customer customer) {
        log.info("Start Creating new account {}", customer.getMobileNumber());
        Accounts account = new Accounts();
        account.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        account.setAccountNumber(randomAccNumber);
        account.setAccountType(AccountsConstants.SAVING);
        account.setBranchAddress(AccountsConstants.ADDRESS);
        account.setCreatedAt(LocalDateTime.now());
        account.setCreatedBy("Anonymous");
        log.info("End Creating new account {}", customer.getMobileNumber());
        return account;
    }

    @Override
    public CustomerDto fetchAccount(String mobleNumber) {
        log.info("Start Fetching account {}", mobleNumber);
        // check if customer with mobile number is existing
        Customer customer = customerRepository.findByMobileNumber(mobleNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobleNumber)
        );
        // check if account with mobile number is existing
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Accounts", "customerId", mobleNumber)
        );
        //mapping data
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));

        log.info("End Fetching account {}", mobleNumber);
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        log.info("Start Updating account {}", customerDto.getName());
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountsDto();
        if (accountsDto != null) {
            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            // mapping data account
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            // save accounts
            accounts = accountsRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString())
            );
            // mapping data customer
            CustomerMapper.mapToCustomer(customerDto, customer);
            // save customer
            customerRepository.save(customer);

            isUpdated = true;
        }
        log.info("End Updating account {}", customerDto.getName());
        return isUpdated;
    }

    @Override
    public boolean deleteAccount(String mobleNumber) {
        log.info("Start Deleting account {}", mobleNumber);
        Customer customer = customerRepository.findByMobileNumber(mobleNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobleNumber)
        );
        accountsRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        log.info("End Deleting account {}", mobleNumber);
        return true;
    }


}
