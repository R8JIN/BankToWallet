package com.factorymethod.digitalwallet.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleAssignRequest {

    Set<String> role;
}
