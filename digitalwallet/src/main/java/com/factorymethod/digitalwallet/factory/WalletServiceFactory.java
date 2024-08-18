package com.factorymethod.digitalwallet.factory;

import com.factorymethod.digitalwallet.concrete_class.Esewa;
import com.factorymethod.digitalwallet.concrete_class.FonePay;
import com.factorymethod.digitalwallet.concrete_class.ImePay;

import com.factorymethod.digitalwallet.exception.WalletServiceException;
import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.service.UserService;
import com.factorymethod.digitalwallet.service.WalletLogService;
import com.factorymethod.digitalwallet.service.WalletService;

import com.factorymethod.digitalwallet.service.WalletStatementService;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WalletServiceFactory {


    protected RestTemplate restTemplate;
    protected WalletStatementService walletStatementService;
    protected UserService userService;
    public WalletServiceFactory(RestTemplate restTemplate, WalletStatementService
                                walletStatementService, UserService userService
    ) {

        this.restTemplate = restTemplate;
        this.walletStatementService = walletStatementService;
        this.userService = userService;
    }

    public WalletService getService(String service) throws WalletServiceException {

        if(service == null){
            return null;
        }
        if(service.equalsIgnoreCase("ESEWA")){
            return new Esewa(restTemplate, walletStatementService, userService
            );
        }
        else if(service.equalsIgnoreCase("FONEPAY")){
            return new FonePay(restTemplate, walletStatementService, userService
            );
        }
        else if (service.equalsIgnoreCase("IMEPAY")){
            return new ImePay(restTemplate
            );
        }
        throw new WalletServiceException("Digital Wallet not available");
    }

}

