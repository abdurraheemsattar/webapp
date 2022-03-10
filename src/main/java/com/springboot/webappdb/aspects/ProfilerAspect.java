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

    //    @Around("execution(* com.springboot.webappdb.*.*.*(..))")
    @Around("execution(* com.springboot.webappdb.topics.*.*(..))")
    public Object profile(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        log.info("ServicesProfiler.profile(): Going to call the method: {}", proceedingJoinPoint.getSignature().getName());
        //-------
        Object output = proceedingJoinPoint.proceed();
        log.info("ServicesProfiler.profile(): Method execution completed.");
        //-------
        long elapsedTime = System.currentTimeMillis() - start;
        log.info("ServicesProfiler.profile(): Method execution time: " + elapsedTime + " milliseconds.");

        return output;
    }

    //@After("execution(* com.springboot.webappdb.*.*.*(..))")
    @After("execution(* com.springboot.webappdb.topics.*.*(..))")
    public void profileMemory() {
        log.info("JVM memory in use = {}", (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
    }


}
