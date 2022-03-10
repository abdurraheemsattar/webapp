package com.springboot.webappdb.aspects;

import io.micrometer.core.instrument.*;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;

@Aspect
@Slf4j

public class MonitoringTimedAspect {

//    @Around("execution(* com.springboot.webappdb.*.*.*(..))")


    public static final String DEFAULT_METRIC_NAME = "method.timed";
    public static final String EXCEPTION_TAG = "exception";
    private final MeterRegistry registry;
    private final Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint;

    public MonitoringTimedAspect() {
        this(Metrics.globalRegistry);
    }


    // =========== MonitoringTimedAspect ===================
    public MonitoringTimedAspect(MeterRegistry registry) {
        this(
                registry,
                pjp ->
                        Tags.of(
                                "class",
                                pjp.getStaticPart().getSignature().getDeclaringTypeName(),
                                "method",
                                pjp.getStaticPart().getSignature().getName()));
    }

    public MonitoringTimedAspect(
            MeterRegistry registry, Function<ProceedingJoinPoint, Iterable<Tag>> tagsBasedOnJoinPoint) {
        this.registry = registry;
        this.tagsBasedOnJoinPoint = tagsBasedOnJoinPoint;
    }


    // =========== processWithTimer ===================
    private Object processWithTimer(ProceedingJoinPoint pjp, MonitoringTimed timed, String metricName)
            throws Throwable {
        Timer.Sample sample = Timer.start(registry);
        String exceptionClass = "none";

        try {
            return pjp.proceed();
        } catch (Exception ex) {
            exceptionClass = ex.getClass().getSimpleName();
            throw ex;
        } finally {
            try {
                sample.stop(
                        Timer.builder(metricName)
                                .description(timed.description().isEmpty() ? null : timed.description())
                                .tags(timed.extraTags())
                                .tags(EXCEPTION_TAG, exceptionClass)
                                .tags(tagsBasedOnJoinPoint.apply(pjp))
                                .publishPercentileHistogram(timed.histogram())
                                .publishPercentiles(timed.percentiles().length == 0 ? null : timed.percentiles())
                                .register(registry));
            } catch (Exception e) {
                // ignoring on purpose
            }
        }
    }


    // =========== processWithLongTaskTimer ===================
    private Object processWithLongTaskTimer(
            ProceedingJoinPoint pjp, MonitoringTimed timed, String metricName) throws Throwable {

        Optional<LongTaskTimer.Sample> sample = buildLongTaskTimer(pjp, timed, metricName).map(LongTaskTimer::start);

        try {
            return pjp.proceed();
        } finally {
            try {
                sample.ifPresent(LongTaskTimer.Sample::stop);
            } catch (Exception e) {
                // ignoring on purpose
            }
        }
    }


    // =========== buildLongTaskTimer ===================
    private Optional<LongTaskTimer> buildLongTaskTimer(
            ProceedingJoinPoint pjp, MonitoringTimed timed, String metricName) {
        try {
            return Optional.of(
                    LongTaskTimer.builder(metricName)
                            .description(timed.description().isEmpty() ? null : timed.description())
                            .tags(timed.extraTags())
                            .tags(tagsBasedOnJoinPoint.apply(pjp))
                            .register(registry));
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    // =========== timeThisMethod ===================
    private Object timeThisMethod(ProceedingJoinPoint pjp, MonitoringTimed timed, String metricName)
            throws Throwable {
        Object response;
        if (!timed.longTask()) {
            response = processWithTimer(pjp, timed, metricName);
        } else {
            response = processWithLongTaskTimer(pjp, timed, metricName);
        }
        if (timed.loggingEnabled()) {
            log.info("{} {}", response, pjp.getArgs());
        }
        return response;
    }


    // =========== generateMetricName ===================
    private String generateMetricName(ProceedingJoinPoint pjp, MonitoringTimed timed) {
        if (!timed.value().isEmpty()) {
            return timed.value();
        }
        return DEFAULT_METRIC_NAME;
    }


    // =========== timedMethod ===================
    @Around("execution (* com.springboot.webappdb.aspects.MonitoringTimed * *.*(..))")
    public Object timedMethod(ProceedingJoinPoint pjp) throws Throwable {
        Method method = ((MethodSignature) pjp.getSignature()).getMethod();
        MonitoringTimed timed = method.getAnnotation(MonitoringTimed.class);

        if (timed == null) {
            method = pjp.getTarget().getClass().getMethod(method.getName(), method.getParameterTypes());
            timed = method.getAnnotation(MonitoringTimed.class);
        }

        final String metricName = generateMetricName(pjp, timed);
        return timeThisMethod(pjp, timed, metricName);

    }


    // =========== timeThisMethod ===================
    public Object timeThisMethod(ProceedingJoinPoint pjp, MonitoringTimed timed) throws Throwable {
        final String metricName = generateMetricName(pjp, timed);
        return timeThisMethod(pjp, timed, metricName);
    }


}
