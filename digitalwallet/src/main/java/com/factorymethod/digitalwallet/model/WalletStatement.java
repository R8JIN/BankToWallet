package com.factorymethod.digitalwallet.model;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class WalletStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String recipientId;
    Double amount;
    String remarks;
    String digitalWallet;
    LocalDateTime transactionDate;

    @ManyToOne
    User user;


}
