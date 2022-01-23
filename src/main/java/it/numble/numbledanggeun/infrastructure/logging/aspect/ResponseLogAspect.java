package it.numble.numbledanggeun.infrastructure.logging.aspect;

import it.numble.numbledanggeun.domain.shared.ResponseFormat;
import it.numble.numbledanggeun.domain.shared.ServletExtractor;
import it.numble.numbledanggeun.domain.shared.ServletExtractor.Servlets;
import it.numble.numbledanggeun.infrastructure.logging.model.LogScheme;
import it.numble.numbledanggeun.infrastructure.logging.model.LogType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

@Slf4j
@Component
@Aspect
public class ResponseLogAspect {

    @Pointcut("execution(* it.numble.numbledanggeun.interfaces.controller.*.*(..))")
    public void logPointCut() {}

    @Around("logPointCut()")
    public Object printResponseLog(ProceedingJoinPoint joinPoint) throws Throwable {
        final Object result = joinPoint.proceed();
        final String methodName = joinPoint.getSignature().toShortString();
        final Servlets servlets = ServletExtractor.get(RequestContextHolder.currentRequestAttributes());
        final ResponseFormat<?> response = ResponseFormat.of(result);

        try {
            final LogScheme logScheme = new LogScheme(LogType.RESPONSE, servlets, response, methodName);
            log.info("{}", logScheme);
        } catch (Exception e) {
            log.error("ResponseLogAspect error : " + e.getMessage());
        }

        return result;
    }
}
