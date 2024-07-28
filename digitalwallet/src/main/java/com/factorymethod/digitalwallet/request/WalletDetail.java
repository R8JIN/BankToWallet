package com.factorymethod.digitalwallet.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class WalletDetail {

    Double amount;
    String recipientId;
    String remarks;
    String digitalWallet;


    @Override
    public String toString() {
        return "{" +
                "amount=" + amount  +
                ", recipientId=" + recipientId +
                ", digitalWallet=" + digitalWallet +
                ", remarks=" + remarks +
                '}';
    }
}

