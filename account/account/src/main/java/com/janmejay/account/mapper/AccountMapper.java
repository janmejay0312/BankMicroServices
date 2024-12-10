package com.janmejay.account.mapper;

import com.janmejay.account.dto.AccountsDto;
import com.janmejay.account.entity.Account;

public class AccountMapper {

    public static  AccountsDto mapToAccountDto(Account accounts, AccountsDto accountsDto){

        accountsDto.setAccountNo(accounts.getAccountNo());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static  Account mapToAccount(Account accounts, AccountsDto accountsDto){

        accounts.setAccountNo(accountsDto.getAccountNo());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
