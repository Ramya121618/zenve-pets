package com.zenve.pets.controller;

import com.zenve.pets.entity.User;
import com.zenve.pets.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        User savedUser = userService.registerUser(user);

        savedUser.setPassword(null);

        return savedUser;
    }
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userService.loginUser(
                user.getEmail(),
                user.getPassword()
        );
    }
    }

