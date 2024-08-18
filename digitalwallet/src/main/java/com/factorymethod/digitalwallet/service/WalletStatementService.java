package com.factorymethod.digitalwallet.service;

import com.factorymethod.digitalwallet.model.WalletStatement;
import com.factorymethod.digitalwallet.repository.WalletStatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.factorymethod.digitalwallet.repository.WalletStatementRepository.Spec.getByUserName;

@Service
public class WalletStatementService {

    @Autowired
    private WalletStatementRepository walletStatementRepository;


    public List<WalletStatement> getAll(){
        return walletStatementRepository.findAll();
    }

    public WalletStatement saveStatement(WalletStatement walletStatement){
        return walletStatementRepository.save(walletStatement);
    }

    public List<WalletStatement> getAllByUserName(String username){
        return walletStatementRepository.findAll(getByUserName(username));
    }

    public  void deleteAll(){
        walletStatementRepository.deleteAll();
    }

    public void deleteByUser(String username){
        walletStatementRepository.deleteAll(getAllByUserName(username));
    }
}
