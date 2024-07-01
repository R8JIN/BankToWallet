package com.rest.template.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Aspect
@Component
@Slf4j
public class WalletServiceAspect {

    @Pointcut("execution(* com.rest.template.controller..*(..))")
    public void applicationPackagePointcut() {

    }

    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {

        System.out.println("\n----------------------------------------------------------------\n");
        log.info("\nEntering method: {} with arguments: {}\n",
                joinPoint.getSignature().toShortString(),
                joinPoint.getArgs());
    }

    @Around("applicationPackagePointcut()")
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object output = pjp.proceed();
        long elapsedTime = System.currentTimeMillis() - start;

        System.out.println("\n----------------------------------------------------------------\n");
        System.out.println(pjp.getSignature() + " executed in " + elapsedTime + "ms\n");
        log.info("\nTransaction completed in {} ms\n", elapsedTime);

        return output;
    }

    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("\n----------------------------------------------------------------\n");
        log.info("\nExiting method: {} with result: {}\n",
                joinPoint.getSignature().toShortString(),
                result);
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "error")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable error) {
        System.out.println("\n----------------------------------------------------------------\n");
        log.error("\nMethod: {} threw an exception: {}\n",
                joinPoint.getSignature().toShortString(),
                error.getMessage());
    }
}
