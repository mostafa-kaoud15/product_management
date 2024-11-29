package com.springboot.spring_security.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.spring_security.dto.RegisterUser;
import com.springboot.spring_security.model.User;
import com.springboot.spring_security.reposiroty.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository repo;
    private final PasswordEncoder passEncoder;

    public UserService(UserRepository repo) {
        this.repo = repo;
        this.passEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException(username));
    }

    public User login(RegisterUser registerUser) {
        User user = new User(registerUser.getUsername(), registerUser.getEmail(),passEncoder.encode(registerUser.getPassword()));
        user.setRoles(List.of("user"));
        user.setEnabled(true);
        return repo.save(user);
    }
    
}
