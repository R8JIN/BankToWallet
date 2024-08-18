package com.factorymethod.digitalwallet.aspect;


import com.factorymethod.digitalwallet.model.WalletLog;
import com.factorymethod.digitalwallet.repository.WalletLogRepository;
import com.factorymethod.digitalwallet.request.WalletDetail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class WalletServiceAspect {


    private final WalletLogRepository walletLogRepository;

    @Pointcut("execution(* com.factorymethod.digitalwallet.controller.WalletController.loadBalance(..))")
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

        WalletLog walletLog = new WalletLog();
        System.out.println("\n----------------------------------------------------------------\n");

        Object[] requests =  pjp.getArgs();
        WalletDetail request = (WalletDetail) requests[0];
        log.info("\n Recipient ID:{}", request.getRecipientId());


        walletLog.setRecipientId(request.getRecipientId());
        walletLog.setRequestPayload(request.toString());

        long start = System.currentTimeMillis();

        walletLog.setRequestTimeStamp(LocalDateTime.now());
        ResponseEntity output = (ResponseEntity) pjp.proceed();

        long elapsedTime = System.currentTimeMillis() - start;
        walletLog.setResponseTimeStamp(LocalDateTime.now());

        System.out.println("\n----------------------------------------------------------------\n");
        System.out.println(pjp.getSignature() + " executed in " + elapsedTime + "ms\n");
        log.info("\nTransaction completed in {} ms\n", elapsedTime);

        System.out.println(output.getBody().getClass());
        HashMap<String, Object> outputHashMap = (HashMap<String, Object>) output.getBody();

        System.out.println(outputHashMap.get("code"));
//        System.out.println(responsePayload.get("code"));
        String jsonLikePart = stringPayload(output.toString());
        String code = stringObjectMap(jsonLikePart.substring(
                        jsonLikePart.indexOf("{")+1,
                        jsonLikePart.lastIndexOf("}")
                )
        );

        log.info("\nRequest Value: {}", jsonLikePart);


        walletLog.setResponsePayload(jsonLikePart);
        walletLog.setStatus(code);
        walletLogRepository.save(walletLog);
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

    private String stringPayload(String output){

        int startIndex = output.indexOf("{");
        int endIndex = output.lastIndexOf("}");


        return output.substring(startIndex, endIndex + 1);
    }

    private String stringObjectMap(String jsonLikePart){

        String[] parts = jsonLikePart.split(",");

        for (String part:parts) {
            String[] keyValue = part.split("=", 2);
            System.out.println("Key and Value:" +keyValue.length);
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            if(key.equalsIgnoreCase("code")){
                return value;
            }
        }
        return "";
    }

}
