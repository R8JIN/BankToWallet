package com.rest.template.controller;


import com.rest.template.mapstruct.dto.WalletAccountGetDto;
import com.rest.template.model.Demo;
import com.rest.template.request.Account;
import com.rest.template.service.WalletAccountService;
import io.micrometer.common.util.StringUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;


import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/wallet-account")
@AllArgsConstructor
@Tag(
        name = "Wallet Account API",
        description = "This is the description of Digital Wallet Account API"
)
public class WalletAccountController extends BaseController {

    @Autowired
    private RestTemplate restTemplate;
    private final WalletAccountService walletAccountService;

    private final WebClient webClient;


    @GetMapping("/chalxa")
    public ResponseEntity<Object> dockerRuns(){
        try {
            return ResponseEntity.ok(buildResponse("Chalyo"));
        }
        catch (Exception e){
            System.out.printf("Message e: %s\n", e.getMessage());
            return ResponseEntity.ok(buildResponse(null, "Chaldaina"));
        }
    }

    @GetMapping
    @Operation(
            summary = "Find account by recipient Id",
            description = "Get endpoint to obtain user account by recipient Id")
    public ResponseEntity<Object> findByRecipientId(
            @RequestParam String recipientId,
            @RequestParam String digitalWallet){

        try {

            if(recipientId== null){

                return ResponseEntity.
                        status(HttpStatus.BAD_REQUEST).
                        body("You need to add recipient id");
            }
            else if(digitalWallet==null){
                return ResponseEntity.
                        status(HttpStatus.BAD_REQUEST).
                        body("Digital Wallet type is missing");
            }

            WalletAccountGetDto walletAccount = (WalletAccountGetDto)
                    walletAccountService.findUser(digitalWallet, recipientId);
           if (walletAccount == null) {

                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Wallet Account not found");

            }

//            Thread.sleep(Duration.ofSeconds(10));
            return ResponseEntity.ok(buildResponse(walletAccount));

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.valueOf(500)).body("An unexpected error occurred");
        }
    }

    @PutMapping
    @Operation(
            summary = "Update Amount of recipient's wallet",
            description = "Put endpoint to update amount of recipient's wallet")
    public ResponseEntity<Object> updateWalletBalance(
            @RequestParam Double amount,
            @RequestParam String recipientId,
            @RequestParam String walletService){

        WalletAccountGetDto walletAccountGetDto = (WalletAccountGetDto) walletAccountService.updateWalletBalance(
                amount,
                recipientId,
                walletService);

        return ResponseEntity.ok(buildResponse(walletAccountGetDto));
    }


    @GetMapping("/rest-template")
    public ResponseEntity<Object> getWalletAccount(){

        String url = "http://localhost:8081/wallet-account?digitalWallet=esewa&recipientId=9868205857";
        Map<String, Object> response = new HashMap<>();

        response = restTemplate.getForObject(url, HashMap.class);

        assert response != null;
        return ResponseEntity.ok(response.get("data"));

    }


    @GetMapping("/web-client")
    public ResponseEntity<Object> getRecipientWebClient(){

        return webClient.get()
                .uri("/wallet-account?digitalWallet=esewa&recipientId=9868205857")
                .retrieve()
                .bodyToMono(Object.class)
                .map(recipient -> {

                    System.out.println("Recipient: " + recipient);
                    return ResponseEntity.ok(recipient);
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient not found")).block();

    }

    @PutMapping("/web-client")
    public Object updateAmountWebClient(){

        return webClient.put()
                .uri("/wallet-account?amount=789&walletService=esewa&recipientId=9868205857")
                .retrieve().
                bodyToMono(Object.class)
                .map(recipient ->{
                    System.out.println("Update Value: " + recipient);
                    return ResponseEntity.ok(recipient);
                })
                .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_MODIFIED).body("Amount not updated")).block();
    }

    @PutMapping("/rest-template")
    public ResponseEntity<Object> restTemplateUpdate() {
        String url = "http://localhost:8081/wallet-account?amount=789&walletService=esewa&recipientId=9868205857";

        HashMap response = restTemplate.exchange(url, HttpMethod.PUT,
                null, HashMap.class).getBody();

        assert response != null;
        return ResponseEntity.ok(response.get("data"));

    }
}
