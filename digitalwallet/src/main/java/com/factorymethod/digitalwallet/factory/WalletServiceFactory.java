package com.factorymethod.digitalwallet.factory;

import com.factorymethod.digitalwallet.concrete_class.Esewa;
import com.factorymethod.digitalwallet.concrete_class.FonePay;
import com.factorymethod.digitalwallet.concrete_class.ImePay;

import com.factorymethod.digitalwallet.exception.WalletServiceException;
import com.factorymethod.digitalwallet.service.WalletService;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class WalletServiceFactory {


    protected RestTemplate restTemplate;
    public WalletServiceFactory(RestTemplate restTemplate
    ) {

        this.restTemplate = restTemplate;
    }

    public WalletService getService(String service) throws WalletServiceException {

        if(service == null){
            return null;
        }
        if(service.equalsIgnoreCase("ESEWA")){
            return new Esewa(restTemplate
            );
        }
        else if(service.equalsIgnoreCase("FONEPAY")){
            return new FonePay(restTemplate
            );
        }
        else if (service.equalsIgnoreCase("IMEPAY")){
            return new ImePay(restTemplate
            );
        }
        throw new WalletServiceException("Digital Wallet not available");
    }

}

