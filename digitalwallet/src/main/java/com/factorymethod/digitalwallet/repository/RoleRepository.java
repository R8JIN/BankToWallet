package com.factorymethod.digitalwallet.repository;

import com.factorymethod.digitalwallet.model.ERole;
import com.factorymethod.digitalwallet.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
