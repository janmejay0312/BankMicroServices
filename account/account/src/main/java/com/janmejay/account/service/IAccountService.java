package com.janmejay.account.service;

import com.janmejay.account.dto.AccountsDto;
import com.janmejay.account.dto.CustomerDto;
import com.janmejay.account.entity.Customer;

public interface IAccountService {


    public Customer createAccount(CustomerDto customerDto);

    public CustomerDto fetchAccount(String mobileNumber );

    public boolean updateCustomer(CustomerDto customerDto);

    public boolean deleteCustomer(String mobileNumber);
}
