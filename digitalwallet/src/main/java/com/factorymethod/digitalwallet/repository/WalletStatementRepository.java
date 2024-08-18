package com.factorymethod.digitalwallet.repository;


import com.factorymethod.digitalwallet.model.WalletStatement;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletStatementRepository extends JpaRepository<WalletStatement, Long>,
        JpaSpecificationExecutor<WalletStatement> {


    interface Spec{
        static Specification<WalletStatement> getByUserName(String username){

            return (root, query, criteriaBuilder) -> {
                Join<Object, Object> userJoin = root.join("user", JoinType.INNER);
                return criteriaBuilder.equal(userJoin.get("username"), username);
            };
        }
    }
}
