package co.id.mastama.accounts.service.impl;

import co.id.mastama.accounts.constants.AccountsConstants;
import co.id.mastama.accounts.dto.CustomerDto;
import co.id.mastama.accounts.entity.Accounts;
import co.id.mastama.accounts.entity.Customer;
import co.id.mastama.accounts.exception.CustomerAlreadyExistsException;
import co.id.mastama.accounts.mapper.CustomerMapper;
import co.id.mastama.accounts.repository.AccountsRepository;
import co.id.mastama.accounts.repository.CustomerRepository;
import co.id.mastama.accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
