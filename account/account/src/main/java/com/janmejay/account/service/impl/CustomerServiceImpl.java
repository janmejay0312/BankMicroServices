package com.janmejay.account.service.impl;

import com.janmejay.account.dto.*;
import com.janmejay.account.entity.Account;
import com.janmejay.account.entity.Customer;
import com.janmejay.account.exceptions.ResourceNotFoundException;
import com.janmejay.account.mapper.AccountMapper;
import com.janmejay.account.mapper.CustomerMapper;
import com.janmejay.account.repository.AccountRepository;
import com.janmejay.account.repository.CustomerRepository;
import com.janmejay.account.service.ICustomerService;
import com.janmejay.account.service.client.CardFeignClient;
import com.janmejay.account.service.client.LoanFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    CardFeignClient cardFeignClient;
    @Autowired
    LoanFeignClient loanFeignClient;
    /**
     *
     * @param mobileNumber
     * @return CustomerDetailDto
     */
    @Override
    public CustomerDetailDto fetchCustomerDetail(String mobileNumber, String correlationId) {
        Optional<Customer> customerIfPresent = customerRepository.findByMobileNumber(mobileNumber);

        if(customerIfPresent.isEmpty()){
            throw  new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber);
        }
        Customer customer = customerIfPresent.get();

        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "CustomerId", String.valueOf(customer.getCustomerId())));

        CustomerDetailDto customerDetailDto =  CustomerMapper.mapToCustomerDetailDto(customer, new CustomerDetailDto());
        customerDetailDto.setAccountDto(AccountMapper.mapToAccountDto(account, new AccountsDto()));

        ResponseEntity<LoansDto> loansDtoResponse = loanFeignClient.fetchLoanDetails(correlationId,mobileNumber);

        if(loansDtoResponse != null)
            customerDetailDto.setLoansDto(loansDtoResponse.getBody());

        ResponseEntity<CardsDto> cardsDtoResponse = cardFeignClient.fetchCardDetails(correlationId,mobileNumber);

        if(cardsDtoResponse != null)
            customerDetailDto.setCardsDto(cardsDtoResponse.getBody());
        return customerDetailDto;
    }
}
