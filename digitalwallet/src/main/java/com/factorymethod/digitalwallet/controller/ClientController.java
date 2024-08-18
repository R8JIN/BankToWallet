package com.factorymethod.digitalwallet.controller;


import com.factorymethod.digitalwallet.model.User;
import com.factorymethod.digitalwallet.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpClient;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
@Tag(
        name = "Get All Clients",
        description = "This is the description of API to get all clients "
)
@PreAuthorize("hasRole('ADMIN')")
public class ClientController extends BaseController{
    private final UserService userService;

    @GetMapping

    public ResponseEntity<Object> getAllClients(Principal principal) {

        System.out.println("User name: " + principal.getName());
//        System.out.println("Token: )
        List<User> user = userService.getAllWalletUser(principal.getName());
        return ResponseEntity.ok(buildResponse(user));
    }



}
