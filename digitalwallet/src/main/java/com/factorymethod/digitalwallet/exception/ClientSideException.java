package com.factorymethod.digitalwallet.exception;

import org.springframework.web.client.RestClientException;

public class ClientSideException extends RestClientException {

    public ClientSideException(String message){
        super(message);
    }
}
