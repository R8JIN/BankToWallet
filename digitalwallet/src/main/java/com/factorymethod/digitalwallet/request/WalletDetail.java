package com.factorymethod.digitalwallet.request;

import com.factorymethod.digitalwallet.model.User;
import jakarta.persistence.ManyToOne;
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
    String username;

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

