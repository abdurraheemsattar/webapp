package com.springboot.webappdb.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class LoggingAspects {

    private Logger log = LoggerFactory.getLogger(this.getClass());


    //   star for any return type, this package, for any class, for any method, for any parameter
//    @Before("execution(* com.springboot.webappdb.*.*(..))")
//    @Before("execution(* com.springboot.webappdb.topics.*.*(..))")
    @Before("execution(* com.springboot.webappdb.*.*.*(..))")
    public void beforeLogger(JoinPoint joinPoint) {
        //joinPoint.getSignature().getName();
        log.trace(joinPoint.getSignature() + " method called");
    }


}
