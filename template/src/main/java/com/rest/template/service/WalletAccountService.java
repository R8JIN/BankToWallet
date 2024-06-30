package com.rest.template.service;

import com.rest.template.mapstruct.mapper.WalletAccountMapper;
import com.rest.template.model.WalletAccount;
import com.rest.template.repository.WalletAccountRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

import static com.rest.template.repository.WalletAccountRepository.Specs.hasRecipientId;

@Service
@AllArgsConstructor
public class WalletAccountService {
    private final WalletAccountRepository walletAccountRepository;
    private final WalletAccountMapper walletAccountMapper;


    public Object findUser(String digitalWallet, String recipientId){

        List<WalletAccount> walletAccountList = walletAccountRepository.findAll(
                        hasRecipientId(recipientId, digitalWallet));
        if(walletAccountList.isEmpty()){
            return null;
        }
        return walletAccountMapper.toDto(walletAccountList.getFirst());
    }

    public Object updateWalletBalance(Double amount, String recipientId, String walletService){

        List<WalletAccount> walletAccountList = walletAccountRepository.findAll(
                hasRecipientId(recipientId, walletService));
        if(walletAccountList.isEmpty()){
            return null;
        }
        WalletAccount walletAccount = walletAccountList.getFirst();

        if(walletAccount.getWalletBalance() != null)
            walletAccount.setWalletBalance(walletAccount.getWalletBalance()+amount);
        else
            walletAccount.setWalletBalance(amount);


    return walletAccountMapper.toDto(walletAccountRepository.save(walletAccount));

    }



}
