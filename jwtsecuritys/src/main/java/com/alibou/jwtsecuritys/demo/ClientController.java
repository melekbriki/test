package com.alibou.jwtsecuritys.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("hasRole('CLIENT')")
@RequestMapping("/api/v1/Client")
public class ClientController {

    @GetMapping
    public String get()
    {
        return  "GET::client controller";
    }
    @PostMapping
    public String post()
    {
        return  "POST::client controller";
    }
    @PutMapping
    public String put()
    {
        return  "PUT::client controller";
    }
    @DeleteMapping
    public String delete()
    {
        return  "DELETE::client controller";
    }
}
