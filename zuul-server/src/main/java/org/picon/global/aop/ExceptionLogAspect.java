package org.picon.global.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@Aspect
public class ExceptionLogAspect {
    @AfterThrowing(value = "execution(* org.picon.auth.*(..))", throwing = "exception")
    public void printExceptionLog(JoinPoint jp, Exception exception){
        log.info("=================  Exception Log Start  =================");
        log.info("| Exception Time => "+ LocalDateTime.now());
        log.info("| Exception Method => "+ jp.getSignature().getName());
        log.info("| Exception Message => "+ exception.getMessage());
        log.info("=================   Exception Log End   =================");
    }
}
