package com.factorymethod.digitalwallet.service;


import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.factorymethod.digitalwallet.repository.UserRepository.Specs.hasNotUsername;


@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User getUserByName(String username){
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(null);
    }

    public List<User> getAllWalletUser(String name){
        List<User> userList = userRepository.findAll(hasNotUsername(name));
        log.info("The user list : {}", userList);
        return userList;
    }
}
