package com.factorymethod.digitalwallet.model;


import com.factorymethod.digitalwallet.request.WalletDetail;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class WalletLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String recipientId;
    String responsePayload;
    String requestPayload;
    LocalDateTime requestTimeStamp;
    LocalDateTime responseTimeStamp;
    String status;

}
