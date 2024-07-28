//package com.factorymethod.digitalwallet.repository;
//
//import com.factorymethod.digitalwallet.model.WalletAccount;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface WalletAccountRepository
//        extends JpaRepository<WalletAccount, Long>, JpaSpecificationExecutor<WalletAccount> {
//
//    interface Specs{
//        static Specification<WalletAccount> hasRecipientId(String recipientId, String walletService) {
//            return (root, query, builder) -> builder.and(
//                    builder.equal(root.get("recipientId"), recipientId),
//                    builder.equal(builder.lower(root.get("walletService")), walletService.toLowerCase())
//                    );
//        }
//    }
//}
