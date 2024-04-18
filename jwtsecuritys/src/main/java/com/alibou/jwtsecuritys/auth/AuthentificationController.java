package com.alibou.jwtsecuritys.auth;

import com.alibou.jwtsecuritys.Entities.User;
import com.alibou.jwtsecuritys.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor

public class AuthentificationController {

    private final AuthenticationService service;






    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        var response = service.register(request);
        if (request.isMfaEnabled()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.accepted().build();
    }
    @GetMapping("/getUser")
    public List<User> getUser(){
        return service.getUser();
    }

    @DeleteMapping("/deleteUser")
    public void deleteUser(@RequestParam Integer id) {
        service.deleteUser(id);
    }
    @PutMapping("/updateUser")
    public AuthenticationResponse updateUser(@RequestBody RegisterRequest request) {
        return service.updateUser(request);
    }


    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(
            @RequestBody VerificationRequest verificationRequest
    ) {
        return ResponseEntity.ok(service.verifyCode(verificationRequest));
    }

}
