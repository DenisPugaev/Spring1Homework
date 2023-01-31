package com.gb.spring1.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class MethodExecutionTimerAspect {


    @Pointcut("@within(com.gb.spring1.aspect.annotation.Timer)")
    public void annotatedClassPointcut() {
    }

    @Pointcut("@annotation(com.gb.spring1.aspect.annotation.Timer)")
    public void annotatedMethodPointcut() {
    }

    @Pointcut("annotatedClassPointcut() || annotatedMethodPointcut()")
    public void targetPointcut() {
    }

    @Around("targetPointcut()")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        Object retVal = null;
        long startTime = System.currentTimeMillis();
            retVal = pjp.proceed();
        long stopTime = System.currentTimeMillis();
        String className = pjp.getTarget().getClass().getSimpleName();
        String methodName = pjp.getSignature().getName();
        log.info("Время выполнения: " + className + " # " + methodName + ": " + (stopTime - startTime) + "мс");

        return retVal;
    }

}
