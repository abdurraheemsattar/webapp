package com.springboot.webappdb.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ProfilerAspect {

    private Logger log = LoggerFactory.getLogger(this.getClass());

//    @Around("execution(* com.springboot.webappdb.topics.*.*(..))")

    @Around("execution(* com.springboot.webappdb.*.*.*(..))")
    public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();

        Object output = proceedingJoinPoint.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        log.info("Method execution time: " + elapsedTime + " milliseconds.");

        return output;
    }

    //@After("execution(* com.springboot.webappdb.topics.*.*(..))")
    @After("execution(* com.springboot.webappdb.*.*.*(..))")
    public void profileMemory() {
        log.info("JVM memory in use = {}", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }


}
