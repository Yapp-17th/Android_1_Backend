package org.picon.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Component
@Aspect
@Slf4j
public class RequestLogAspect {

    @Around("execution(* org.picon.controller.*.*(..))")
    public Object printRequestLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

            Object result = proceedingJoinPoint.proceed();
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String controllerName = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();
            String methodName = proceedingJoinPoint.getSignature().getName();

            log.info("=================  Request Log Start  =================");
            log.info("Request Controller => " + controllerName);
            log.info("Request Method => " + methodName);
            log.info("Request URL => " + request.getRequestURL());
            log.info("Request HTTP Method => " + request.getMethod());
            log.info("Request Log Time => " + LocalDateTime.now());
            log.info("=================   Request Log End   =================");

            return result;
    }
}
