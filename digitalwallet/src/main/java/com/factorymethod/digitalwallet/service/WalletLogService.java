package com.factorymethod.digitalwallet.service;

import com.factorymethod.digitalwallet.model.WalletLog;
import com.factorymethod.digitalwallet.repository.WalletLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.factorymethod.digitalwallet.repository.WalletLogRepository.specs.filterByRecipientId;

@Service
public class WalletLogService {

    @Autowired
    private WalletLogRepository walletLogRepository;

    public List<WalletLog> getAllLog(){
        return walletLogRepository.findAll();
    }

    public List<WalletLog> getLogsByName(String recipientId){
        return walletLogRepository.findAll(filterByRecipientId(recipientId));
    }

    public Boolean deleteAllLog(){
        if(!walletLogRepository.findAll().isEmpty()) {
            walletLogRepository.deleteAll();
            return true;
        }
        return false;
    }
}
