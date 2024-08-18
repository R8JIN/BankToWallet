package com.factorymethod.digitalwallet.concrete_class;


import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.model.WalletStatement;
import com.factorymethod.digitalwallet.request.WalletDetail;
import com.factorymethod.digitalwallet.service.UserService;
import com.factorymethod.digitalwallet.service.WalletService;
import com.factorymethod.digitalwallet.service.WalletStatementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;

import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Slf4j
public class Esewa extends WalletService {

    private double walletBalance;

    @Autowired
    public Esewa(RestTemplate restTemplate, WalletStatementService walletStatementService, UserService userService){
        super(restTemplate, walletStatementService, userService);
    }

    @Override
    public boolean verifyRecipientId(String recipientId, String walletService) {

        String url = "http://localhost:8081/wallet-account";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("recipientId", recipientId)
                .queryParam("digitalWallet", walletService);


        ResponseEntity<Object> responseData = restTemplate.exchange(
                builder.toUriString(), HttpMethod.GET, null, Object.class);
        String response = responseData.getStatusCode().toString();

        assert response != null;


        if (!response.contains("OK")) {
            log.error("Incorrect esewa Id");
            return false;
        }
        log.info("Esewa Id verified");
        return true;


    }

    @Override
    public boolean checkBankBalance(Double amount) {
        if(amount <= WalletService.BANK_BALANCE){
            walletBalance = walletBalance + amount;
            WalletService.BANK_BALANCE = WalletService.BANK_BALANCE - amount;
            return true;
        }
        else{
            log.error("Balance insufficient.");
            return false;
        }
    }

    @Override
    public Object loadBalance(WalletDetail walletDetails) {

        Map<String, Object> transactionDetail = new HashMap<>();


            if (checkBankBalance(walletDetails.getAmount())
                    &&
                    verifyRecipientId(walletDetails.getRecipientId(),
                            walletDetails.getDigitalWallet()
                    )
            ) {

                log.info("\nRs.{} loaded into your esewa account ({}).",
                        walletDetails.getAmount(), walletDetails.getRecipientId()
                );
                log.info("\nREMARKS: {}\n", walletDetails.getRemarks());

                String url = "http://localhost:8081/wallet-account?amount=" + walletDetails.getAmount() +
                        "&walletService=" + walletDetails.getDigitalWallet() +
                        "&recipientId=" + walletDetails.getRecipientId();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                Object body = null;

                HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
                ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.PUT,
                        requestEntity, Object.class);

                return saveWalletStatement(walletDetails);
            }


        return null;
    }

}
