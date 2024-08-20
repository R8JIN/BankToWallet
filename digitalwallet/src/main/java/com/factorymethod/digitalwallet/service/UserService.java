package com.factorymethod.digitalwallet.service;


import com.factorymethod.digitalwallet.model.ERole;
import com.factorymethod.digitalwallet.model.Role;
import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.repository.RoleRepository;
import com.factorymethod.digitalwallet.repository.UserRepository;
import com.factorymethod.digitalwallet.request.RoleAssignRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.factorymethod.digitalwallet.repository.UserRepository.Specs.hasNotUsername;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public User getUserByName(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public List<User> getAllWalletUser(String name){
        List<User> userList = userRepository.findAll(hasNotUsername(name));
        log.info("The user list : {}", userList);
        return userList;
    }

    public User getClientById(Long id){
        User user = userRepository.findById(id).get();
        return user;
    }

    public User setUserRole(Long id, RoleAssignRequest roleAssignRequest) {
        User user = userRepository.getReferenceById(id);
        Set<String> strRoles = roleAssignRequest.getRole();

        Set<Role> roles = user.getRoles();
        strRoles.forEach(role -> {
            switch (role) {
                case "admin":
                    Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(adminRole);

                    break;
                case "mod":
                    Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);

                    break;
                default:
                    Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
            }
        });

        user.setRoles(roles);
        return userRepository.save(user);

    }
}
