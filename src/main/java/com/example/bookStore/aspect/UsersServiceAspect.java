package com.example.bookStore.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UsersServiceAspect {
    @Before(value ="execution(* com.example.bookStore.service.UsersService.*(..))")
    public void beforeAdvice(JoinPoint joinPoint){
        log.info("user service is running");
    }

    @After(value ="execution(* com.example.bookStore.service.UsersService.*(..))")
    public void afterAdvice(JoinPoint joinPoint){
        log.info("user service run successfully");
    }
    //todo تمام لاگ ها رو اینجا بیار
}
