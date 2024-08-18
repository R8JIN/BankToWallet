package com.factorymethod.digitalwallet.service;


import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.model.WalletStatement;
import com.factorymethod.digitalwallet.request.WalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.LocalDateTime;


@Service

public abstract class WalletService {

    protected static String user = "Rojin Awal";
    protected static double BANK_BALANCE = 100000.00;
    protected WalletStatementService walletStatementService;
    protected RestTemplate restTemplate;
    protected UserService userService;

    @Autowired
    public WalletService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public WalletService(RestTemplate restTemplate, WalletStatementService walletStatementService, UserService userService) {

        this.restTemplate = restTemplate;
        this.walletStatementService = walletStatementService;
        this.userService = userService;
    }


    public abstract boolean verifyRecipientId(String recipientId, String walletService);
    public abstract boolean checkBankBalance(Double amount);
    public abstract Object loadBalance(WalletDetail walletDetails) throws IOException;

    protected WalletStatement saveWalletStatement(WalletDetail walletDetails){

        WalletStatement walletStatement = WalletStatement.builder().
                recipientId(walletDetails.getRecipientId()).
                digitalWallet(walletDetails.getDigitalWallet()).
                amount(walletDetails.getAmount()).
                remarks(walletDetails.getRemarks()).
                transactionDate(LocalDateTime.now()).
                user(userService.getUserByName(walletDetails.getUsername())).
                build();

        return walletStatementService.saveStatement(walletStatement);
    }
}
