package co.id.mastama.accounts.service;

import co.id.mastama.accounts.dto.CustomerDto;

public interface IAccountsService {
    /**
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);

    /**
     * @param mobleNumber - Input Mobile Number
     * @reutrn Accounts Details based on a given mobileNumber
     */
    CustomerDto fetchAccount(String mobleNumber);
}
