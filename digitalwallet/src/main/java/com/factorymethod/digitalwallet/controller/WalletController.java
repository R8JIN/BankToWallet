package com.factorymethod.digitalwallet.controller;


import com.factorymethod.digitalwallet.exception.ClientSideException;
import com.factorymethod.digitalwallet.exception.ResourceNotFoundException;
import com.factorymethod.digitalwallet.exception.WalletServiceException;
import com.factorymethod.digitalwallet.factory.WalletServiceFactory;
import com.factorymethod.digitalwallet.model.WalletLog;
import com.factorymethod.digitalwallet.model.WalletStatement;
import com.factorymethod.digitalwallet.request.WalletDetail;
import com.factorymethod.digitalwallet.service.WalletLogService;
import com.factorymethod.digitalwallet.service.WalletService;

import com.factorymethod.digitalwallet.service.WalletStatementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.tags.Tags;
import lombok.AllArgsConstructor;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api/digital-wallet")
@AllArgsConstructor
@Tag(
        name = "Bank To Wallet API",
        description = "This is the description of API to transfer the money from bank to wallet"
)

public class WalletController extends BaseController{

    private final WalletServiceFactory walletServiceFactory;
    private final WalletLogService walletLogService;
    private final WalletStatementService walletStatementService;
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping
    @Operation(
            description = "POST endpoint to load balance in wallet.",
            summary = "Load Balance in wallet")
    ResponseEntity<Object> loadBalance(@RequestBody WalletDetail walletDetails){
        try {

            WalletService walletService = walletServiceFactory.getService(
                    walletDetails.getDigitalWallet()
            );

            return ResponseEntity.ok(buildResponse(
                    walletService.loadBalance(walletDetails))
            );

        }
        catch(WalletServiceException | ResourceNotFoundException e){

            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(buildResponse(null, "Please enter valid account number or wallet service"));

        }
        catch(ClientSideException e){

            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(buildResponse(null, e.getMessage()));

        }
        catch (ResourceAccessException | IOException e){

            if( e instanceof ResourceAccessException) {
                System.out.println("Exception: "+ e.getMessage());
                if(e.getMessage().contains("Read")){

                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).
                            body(buildResponse(
                                    null,
                                    "Server taking too long to process.",
                                    "2")
                            );
                }
                else if(e.getMessage().contains("Connection")){

                    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).
                            body(buildResponse(
                                    null,
                                    "Server Down",
                                    "1")
                            );
                }
                return ResponseEntity.status(HttpStatus.BAD_GATEWAY).
                        body(buildResponse(null, e.getMessage()));
            }

            return ResponseEntity.
                    status(HttpStatus.BAD_REQUEST).
                    body(buildResponse(null, ((IOException) e).getMessage()));

        }

    }

    @GetMapping("/wallet-statement")
    @Operation(
            description = "Get endpoint to obtain wallet statement"
    )
    public ResponseEntity<Object> getAllWalletStatement(){
        List<WalletStatement> walletStatements = walletStatementService.getAll();
        if(walletStatements.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponse(null, "No statements"));
        }
        return ResponseEntity.ok(buildResponse(walletStatements));
    }

    @GetMapping("/user-statement")
    @Operation(
            description = "Get endpoint to obtain wallet statement based on Username"
    )
    public ResponseEntity<Object> getWalletStatementUser(@RequestParam String username){

        List<WalletStatement> walletStatements = walletStatementService.getAllByUserName(username);
        if(walletStatements.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponse(null, "No statements"));
        }
        return ResponseEntity.ok(buildResponse(walletStatements));
    }


    @DeleteMapping("/user-statement")
    @Operation(
            description = "Delete endpoint to delete wallet statement"
    )
    public ResponseEntity<Object> deleteStatementUser(@RequestParam String username){

         walletStatementService.deleteByUser(username);
         return ResponseEntity.ok(buildResponse("Deleted"));
    }

    @GetMapping("/wallet-log")
    @Operation(
            description = "Get endpoint to obtain wallet log.",
            summary = "Obtain wallet log"
    )
    public ResponseEntity<Object> getAllWalletLog(){

        List<WalletLog> walletLogs = walletLogService.getAllLog();
        if(walletLogs.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(buildResponse(null, "Log Empty"));
        }
        return ResponseEntity.ok(buildResponse(walletLogs));

    }

    @GetMapping("/wallet-log/{recipientId}")
    @Operation(
            description = "Get endpoint to obtain wallet log by recipient ID.",
            summary = "Obtain wallet log by recipient ID"
    )
    public ResponseEntity<Object> getWalletLogByRecipientId(@PathVariable("recipientId") String recipientId){

        List<WalletLog> walletLogs = walletLogService.getLogsByName(recipientId);
        if(walletLogs.isEmpty()){
            return ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(buildResponse(null, "Log with RecipientID"));
        }
        return ResponseEntity.ok(buildResponse(walletLogs));
    }

    @DeleteMapping("/wallet-log")
    public ResponseEntity<Object> deleteAllLog(){

        if(walletLogService.deleteAllLog()) {
            return ResponseEntity.ok(buildResponse("deleted"));
        }
        else{
            return  ResponseEntity.
                    status(HttpStatus.NOT_FOUND).
                    body(buildResponse(null, "Log is empty"));
        }
    }
    @GetMapping("/testing")
    public ResponseEntity<Object> testConnectionTimeout() {

        String url = "http://httpbin.org/delay/5";

        try {

            restTemplate.getForObject(url, String.class);
            return ResponseEntity.ok(buildResponse("OK"));

        } catch (ResourceAccessException e) {

           if(e.getCause() instanceof java.net.SocketTimeoutException){

               return ResponseEntity.
                       status(HttpStatus.INTERNAL_SERVER_ERROR).
                       body(buildResponse(
                               null,
                               "Read Time Out")
                       );
           }

           return ResponseEntity.
                   status(HttpStatus.INTERNAL_SERVER_ERROR).
                   body(buildResponse(
                           null,
                           "Connection Time Out")
                   );
        }

    }




}
