package com.rest.template.mapstruct.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class WalletAccountPostDto {

    Long id;
    String firstName;
    String lastName;
    String recipientId;
    String walletService;
    Double walletBalance;
}
