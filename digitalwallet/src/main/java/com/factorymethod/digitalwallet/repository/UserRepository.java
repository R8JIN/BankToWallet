package com.factorymethod.digitalwallet.repository;

import com.factorymethod.digitalwallet.model.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    interface  Specs{
        static Specification<User> hasNotUsername(String username){
            return (root, query, builder)-> builder.not(
                    builder.equal(root.get("username"),
                    username));
        }
    }
}