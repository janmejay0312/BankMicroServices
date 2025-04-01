package com.janmejay.account.function;

import com.janmejay.account.service.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountFunction {
private  static  final Logger logger = LoggerFactory.getLogger(AccountFunction.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountService iAccountService){
        return accountNumber ->{
            logger.info("Updating Communication status for the account number:"+ accountNumber.toString());
            iAccountService.updateCommunicationSw(accountNumber);
        };
    }
}
