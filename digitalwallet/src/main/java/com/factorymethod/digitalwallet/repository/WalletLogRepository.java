package com.factorymethod.digitalwallet.repository;


import com.factorymethod.digitalwallet.model.WalletLog;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletLogRepository extends JpaRepository<WalletLog, Long>, JpaSpecificationExecutor<WalletLog> {

    interface specs{
       static Specification<WalletLog> filterByRecipientId(String recipientId){
            return ((root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("recipientId"), recipientId));
        }
    }
}
