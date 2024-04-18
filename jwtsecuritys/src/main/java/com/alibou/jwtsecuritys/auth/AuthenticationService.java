package com.alibou.jwtsecuritys.auth;


import com.alibou.jwtsecuritys.Config.JwtService;
import com.alibou.jwtsecuritys.Entities.Role;
import com.alibou.jwtsecuritys.Entities.Token;
import com.alibou.jwtsecuritys.Entities.TokenType;
import com.alibou.jwtsecuritys.Entities.User;
import com.alibou.jwtsecuritys.Repository.TokenRepository;
import com.alibou.jwtsecuritys.Repository.UserRepository;
import com.alibou.jwtsecuritys.tfa.TwoFactorAuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Builder
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final TokenRepository tokenRepository;

    private  final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    private final TwoFactorAuthenticationService tfaService;
    public AuthenticationResponse register(RegisterRequest request) {
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .mfaEnabled(request.isMfaEnabled())
                .build();
        // if MFA enabled --> Generate Secret
        if (request.isMfaEnabled()) {
            user.setSecret(tfaService.generateNewSecret());
        }
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())

                .build();
    }

    public List<User> getUser(){
        return (List<User>) repository.findAll();
    }


    public void deleteUser(Integer id) {
        // Delete tokens associated with the user
        tokenRepository.deleteByUserId(id);

        // Delete the user
        repository.deleteById(id);
    }

    public void deleteUserForAdmin(Integer id) {
        // Retrieve the user entity from the database
        User user = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));


        // Delete the user
        repository.delete(user);
    }
    public AuthenticationResponse updateUser(RegisterRequest request) {
        User user = repository.findById(request.getid())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Update the user
        repository.save(user);

        // Return the updated user details
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .secretImageUri(tfaService.generateQrCodeImageUri(user.getSecret()))
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        if (user.isMfaEnabled()) {
            return AuthenticationResponse.builder()
                    .accessToken("")
                    .refreshToken("")
                    .mfaEnabled(true)
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .mfaEnabled(false)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = this.repository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user))
            {
                var accessToken = jwtService.generateToken(user);

                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .mfaEnabled(false)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    public AuthenticationResponse verifyCode(
            VerificationRequest verificationRequest
    ) {
        User user = repository
                .findByEmail(verificationRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("No user found with %S", verificationRequest.getEmail()))
                );
        if (tfaService.isOtpNotValid(user.getSecret(), verificationRequest.getCode())) {

            throw new BadCredentialsException("Code is not correct");
        }
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .mfaEnabled(user.isMfaEnabled())
                .build();
    }
}
