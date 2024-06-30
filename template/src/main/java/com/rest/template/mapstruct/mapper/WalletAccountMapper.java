package com.rest.template.mapstruct.mapper;



import com.rest.template.mapstruct.dto.WalletAccountGetDto;
import com.rest.template.mapstruct.dto.WalletAccountPostDto;
import com.rest.template.model.WalletAccount;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface WalletAccountMapper {

    WalletAccount toEntity(WalletAccountPostDto walletAccountPostDto);
    WalletAccountGetDto toDto(WalletAccount walletAccount);
}
