package com.rest.template.request;

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

}

