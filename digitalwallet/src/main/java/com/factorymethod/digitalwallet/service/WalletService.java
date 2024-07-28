package com.factorymethod.digitalwallet.service;


import com.factorymethod.digitalwallet.request.WalletDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public abstract class WalletService {

    protected static String user = "Rojin Awal";
    protected static double BANK_BALANCE = 10000.00;

    protected RestTemplate restTemplate;

    @Autowired
    public WalletService(RestTemplate restTemplate) {

        this.restTemplate = restTemplate;
    }


    public abstract boolean verifyRecipientId(String recipientId, String walletService);
    public abstract boolean checkBankBalance(Double amount);
    public abstract Object loadBalance(WalletDetail walletDetails) throws IOException;
}
