package com.factorymethod.digitalwallet.aspect;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;



@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ClientServiceAspect {



    @Pointcut("execution(* com.factorymethod.digitalwallet.controller.ClientController.getAllClients(..))")
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
    public Object measureExecutionTime(ProceedingJoinPoint pjp) throws Throwable
    {

        System.out.println("\n----------------------------------------------------------------\n");

        Object[] requests =  pjp.getArgs();
        ResponseEntity output = (ResponseEntity) pjp.proceed();


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
