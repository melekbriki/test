package com.alibou.jwtsecuritys.Entities;


import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RequiredArgsConstructor
public enum Permission  {

      ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),

    MANAGER_READ("management:read"),
    MANAGER_UPDATE("management:update"),
    MANAGER_CREATE("management:create"),
    MANAGER_DELETE("management:delete"),


   CLIENT_READ("CLIENT:read"),
    CLIENT_UPDATE("management:update"),
    CLIENT_CREATE("management:create"),
    CLIENT_DELETE("management:delete")

    ;


    @Getter
    private final String permission;
}
