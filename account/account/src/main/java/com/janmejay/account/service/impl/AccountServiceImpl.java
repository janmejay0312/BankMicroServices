package com.janmejay.account.service.impl;

import com.janmejay.account.constants.AccountsConstants;
import com.janmejay.account.dto.AccountMsgDto;
import com.janmejay.account.dto.AccountsDto;
import com.janmejay.account.dto.CustomerDto;
import com.janmejay.account.entity.Account;
import com.janmejay.account.entity.Customer;
import com.janmejay.account.exceptions.AccountNotExistException;
import com.janmejay.account.exceptions.CustomerAlreadyExistException;
import com.janmejay.account.exceptions.CustomerNotExistException;
import com.janmejay.account.exceptions.ResourceNotFoundException;
import com.janmejay.account.mapper.AccountMapper;
import com.janmejay.account.mapper.CustomerMapper;
import com.janmejay.account.repository.AccountRepository;
import com.janmejay.account.repository.CustomerRepository;
import com.janmejay.account.service.IAccountService;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements IAccountService {

    private  static  final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    StreamBridge streamBridge;


    @Override
    public Customer createAccount(CustomerDto customerDto) {


        Customer customer = CustomerMapper.mapToCustomer(new Customer(),customerDto);
        System.out.println(customerDto.toString());
        Optional<Customer> customerIfPresent = customerRepository.findByMobileNumber(customer.getMobileNumber());

        if(customerIfPresent.isPresent()){
            throw  new CustomerAlreadyExistException(" Customer already exist with given mobile number");
        }
        customer.setCreatedBy("Anonymous");
        customer.setCreatedAt(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        Account account = createAccount(customer);
        accountRepository.save(account);
        sendCommunication(savedCustomer, account);
return customer;
    }

    private void sendCommunication(Customer savedCustomer, Account account) {

        var accountsMsgDto = new AccountMsgDto(account.getAccountNo(), savedCustomer.getName(),savedCustomer.getEmail(),
                savedCustomer.getMobileNumber());
        logger.info("sending Communication request for details:{}", accountsMsgDto);
        var result = streamBridge.send("sendCommunication-out-0", accountsMsgDto);
        logger.info("Is the communication request successfully triggered?:{}", result);
    }

    private Account createAccount(Customer customer){

        Account account = new Account();

       // Optional<Account> accountNoIfPresent = accountRepository.findByAccountNo(customer.getCustomerId());



        account.setCustomerId(customer.getCustomerId());

        Random random = new Random();

        Long randomNumber = 1000000000L + random.nextInt(900000000);

//        Optional<Account> accountNoIfPresent = accountRepository.findByAccountNo(customer.getCustomerId());
//
//        if(accountNoIfPresent.isPresent())
//        {
//            throw new AccountAlreadyExistException("Account no Already Exist for ")
//        }
        account.setCreatedBy("Anonymous");
        account.setCreatedAt(LocalDateTime.now());
        account.setAccountNo(randomNumber);
        account.setAccountType(AccountsConstants.SAVINGS);
        account.setBranchAddress(AccountsConstants.ADDRESS);

    return  account;
    }

    public  CustomerDto fetchAccount( String mobileNumber){
        Optional<Customer> customerIfPresent = customerRepository.findByMobileNumber(mobileNumber);

        if(customerIfPresent.isEmpty()){
            throw  new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
    Customer customer = customerIfPresent.get();

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "CustomerId", String.valueOf(customer.getCustomerId())));

        AccountsDto accountsDto = AccountMapper.mapToAccountDto(account, new AccountsDto());
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(accountsDto);

        return customerDto;
    }

    @Override
    public boolean updateCustomer(CustomerDto customerDto) {

        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountDto();

        if(accountsDto != null){
            Account account = accountRepository.findById(accountsDto.getAccountNo()).
                    orElseThrow(()-> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNo().toString()));
            AccountMapper.mapToAccount(account,accountsDto);
            accountRepository.save(account);

            Long customerId = account.getCustomerId();
            Customer customer = customerRepository.findById(customerId).
                    orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId" , customerId.toString()));
            CustomerMapper.mapToCustomer(customer,customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }
            else{
            Customer customer = CustomerMapper.mapToCustomer(new Customer(),customerDto);
            customerRepository.save(customer);
            isUpdated = true;
        }

        return isUpdated;
    }

    @Override
    public boolean deleteCustomer(String mobileNumber) {


        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new ResourceNotFoundException("Customer","mobileNumber",mobileNumber));

            Long customerId = customer.getCustomerId();
            accountRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);


        return true;
    }

    @Override
    public boolean updateCommunicationSw(Long accountNumber) {

        boolean isUpdated = false;

        if(accountNumber!=null)
        {
            Account account = accountRepository.findById(accountNumber).orElseThrow(()->new
                    ResourceNotFoundException("Account","AccountNumber",accountNumber.toString()));

            account.setCommunicationSw(true);
            accountRepository.save(account);
            isUpdated = true;
        }

        return isUpdated;
    }

}
