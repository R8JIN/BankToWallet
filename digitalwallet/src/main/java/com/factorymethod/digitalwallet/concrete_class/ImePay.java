package com.factorymethod.digitalwallet.concrete_class;


import com.factorymethod.digitalwallet.request.WalletDetail;
import com.factorymethod.digitalwallet.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class ImePay extends WalletService {


    @Autowired
    public ImePay(RestTemplate restTemplate){
        super(restTemplate);
    }

    @Override
    public boolean verifyRecipientId(String recipientId, String walletService) {

//        String url = "http://localhost:8081/wallet-account?digitalWallet="
//                + walletService + "&recipientId="+recipientId;
//
//        HashMap response = restTemplate.getForObject(url, HashMap.class);
//        assert response != null;
//        Object data = response.get("data");
//        System.out.println("The response body is:" + data);
//
//        // Using web client
//        WebClient webClient = WebClient.create();
//        HashMap webClientResponse = webClient.get()
//                .uri("http://localhost:8081/wallet-account?digitalWallet="
//                        +  walletService + "&recipientId=" + recipientId
//                )
//                .retrieve()
//                .bodyToMono(HashMap.class)
//                .block();
//
//
//        assert webClientResponse != null;
//        if(!(Boolean)webClientResponse.get("success")){
//            log.error("Incorrect IMEPay Id");
//            return false;
//        }
//        log.info("ImePay Id verified");
//        return true;
        String url = "http://localhost:8081/wallet-account";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("recipientId", recipientId)
                .queryParam("digitalWallet", walletService);

        System.out.println("The uri component Builder: "+ builder.toUriString());

        ResponseEntity<Object> responseData = restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                null,
                Object.class);
        String response = responseData.getStatusCode().toString();

        assert response != null;


        if (!response.contains("OK")) {
            log.error("Incorrect ImePay Id");
            return false;
        }
        log.info("ImePays Id verified");
        return true;

    }

    @Override
    public boolean checkBankBalance(Double amount) {
        return amount <= WalletService.BANK_BALANCE;
    }

    @Override
    public Object loadBalance(WalletDetail walletDetails) {

        Map<String, Object> transactionDetail = new HashMap<>();

        if(checkBankBalance(walletDetails.getAmount()) &&
        verifyRecipientId(walletDetails.getRecipientId(),
                walletDetails.getDigitalWallet())) {

            log.info("\nRs.{} loaded into your IME account ({}).",
                    walletDetails.getAmount(),
                    walletDetails.getRecipientId());
            log.info("\nREMARKS: {}\n", walletDetails.getRemarks());

            String url = "http://localhost:8081/wallet-account?amount=" + walletDetails.getAmount() +
                    "&walletService=" +  walletDetails.getDigitalWallet() +
                    "&recipientId="+walletDetails.getRecipientId();
            ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT,
                    null, Object.class);


//                HashMap webClientResponse = (WebClient.create())
//                        .put()
//                        .uri(url)
//                        .retrieve()
//                        .bodyToMono(HashMap.class)
//                        .block();
//            assert webClientResponse != null;
//            log.info("IME pay balance updated : {}", webClientResponse.get("data"));

            transactionDetail.put("IMEPay Id", walletDetails.getRecipientId());
            transactionDetail.put("Amount", walletDetails.getAmount());
            transactionDetail.put("Remarks", walletDetails.getRemarks());

            return transactionDetail;
        }

        return null;
    }


}
