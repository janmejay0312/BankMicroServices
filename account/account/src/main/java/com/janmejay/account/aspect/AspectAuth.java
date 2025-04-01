package com.janmejay.account.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectAuth {

    @Before(value = "execution(* com.janmejay.account.service.impl.AccountServiceImpl .fetchAccount())")
     public void authAccount(){
        System.out.println("Authenticated");
     }
}
