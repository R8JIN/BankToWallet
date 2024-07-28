//package com.factorymethod.digitalwallet.controller;
//
//import com.factorymethod.digitalwallet.mapstruct.dto.WalletAccountGetDto;
//
//import com.factorymethod.digitalwallet.service.WalletAccountService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.reactive.function.client.WebClient;
//
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("wallet-account")
//@AllArgsConstructor
//public class WalletAccountController extends BaseController {
//
//    @Autowired
//    private RestTemplate restTemplate;
//    private final WalletAccountService walletAccountService;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final WebClient webClient;
//
//    @GetMapping("/rest-template")
//    public ResponseEntity<Object> getWalletAccount(){
//        String url = "http://localhost:8080/wallet-account?digitalWallet=esewa&recipientId=9868205857";
//        Map<String, Object> response = new HashMap<>();
//        response = restTemplate.getForObject(url, HashMap.class);
//        ObjectMapper objectMapper1 = new ObjectMapper();
//        assert response != null;
//        return ResponseEntity.ok(response.get("data"));
//
//    }
//
//    @GetMapping("/web-client")
//    public ResponseEntity<Object> getRecipientWebClient(){
//
//       return webClient.get()
//               .uri("/wallet-account?digitalWallet=esewa&recipientId=9868205857")
//               .retrieve()
//               .bodyToMono(Object.class)
//               .map(recipient -> {
//
//            System.out.println("Recipient: " + recipient);
//            return ResponseEntity.ok(recipient);
//        })
//        .defaultIfEmpty(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recipient not found")).block();
//
//    }
//
//    @GetMapping
//    public ResponseEntity<Object> findByRecipientId(
//            @RequestParam String digitalWallet,
//            @RequestParam String recipientId){
//
//        WalletAccountGetDto walletAccount = (WalletAccountGetDto)
//                walletAccountService.findUser(digitalWallet, recipientId);
//        return ResponseEntity.ok(buildResponse(walletAccount));
//    }
//
//    @PutMapping("/rest-template")
//    public ResponseEntity<Object> restTemplateUpdate() throws IOException {
//        String url = "http://localhost:8080/wallet-account?amount=789&walletService=esewa&recipientId=9868205857";
//
//       HashMap response = restTemplate.exchange(url, HttpMethod.PUT,
//                null, HashMap.class).getBody();
//
//        assert response != null;
//        return ResponseEntity.ok(response.get("data"));
//
//    }
//    @PutMapping
//    public ResponseEntity<Object> updateWalletBalance(
//            @RequestParam Double amount,
//            @RequestParam String recipientId,
//            @RequestParam String walletService){
//
//        WalletAccountGetDto walletAccountGetDto = (WalletAccountGetDto) walletAccountService.updateWalletBalance(
//                amount,
//                recipientId,
//                walletService);
//
//        return ResponseEntity.ok(buildResponse(walletAccountGetDto));
//    }
//
//}
