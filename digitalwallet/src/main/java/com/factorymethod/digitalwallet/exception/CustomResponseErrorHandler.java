package com.factorymethod.digitalwallet.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Slf4j
public class CustomResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        if(response.getStatusCode() == HttpStatus.NOT_FOUND){
            String responseAsString = toString(response.getBody());
            log.error("Not Found Exception: {}", responseAsString);
            throw new ResourceNotFoundException(responseAsString);
        }
        if (response.getStatusCode().is4xxClientError()) {
            String responseAsString = toString(response.getBody());
            log.error("Client Side Error: {}", responseAsString);
            throw new ClientSideException(responseAsString);


        } else if (response.getStatusCode().is5xxServerError()) {
            String responseAsString = toString(response.getBody());
            log.error("Server Side error: {}", responseAsString);
            throw new CustomException(responseAsString);
        }
    }

    String toString(InputStream inputStream) {
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static class CustomException extends IOException {
        public CustomException(String message) {
            super(message);
        }
    }

}
