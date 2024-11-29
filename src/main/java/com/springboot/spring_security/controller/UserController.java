package com.springboot.spring_security.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.spring_security.dto.AuthRequest;
import com.springboot.spring_security.dto.AuthResponse;
import com.springboot.spring_security.dto.RegisterUser;
import com.springboot.spring_security.model.User;
import com.springboot.spring_security.service.UserService;
import com.springboot.spring_security.util.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationManager authenManager;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {

        Authentication authentication = authenManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        User user = (User) authentication.getPrincipal();
        String token = jwtUtil.createToken(user);
        return ResponseEntity.ok().body(new AuthResponse(token, user.getUsername()));

    }

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterUser user) {
        User createdUser = userService.login(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

    }

}
