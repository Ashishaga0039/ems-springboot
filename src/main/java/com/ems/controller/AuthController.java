package com.ems.controller;

import com.ems.entity.User;
import com.ems.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository repo;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        repo.save(user);
        return "User Registered";
    }

    // LOGIN
    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existing = repo.findByEmail(user.getEmail());

        if (existing != null && existing.getPassword().equals(user.getPassword())) {
            return "Login Success";
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}