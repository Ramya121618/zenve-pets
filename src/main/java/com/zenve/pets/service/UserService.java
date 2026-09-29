package com.zenve.pets.service;

import com.zenve.pets.entity.User;
import com.zenve.pets.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;


    private final BCryptPasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public User registerUser(User user) {

        System.out.println("REGISTER CALLED");
        System.out.println("NAME: " + user.getName());
        System.out.println("EMAIL: " + user.getEmail());

        String encodedPassword =
                passwordEncoder.encode(user.getPassword());

        user.setPassword(encodedPassword);

        System.out.println("BEFORE SAVE");

        User savedUser = userRepository.save(user);

        System.out.println("AFTER SAVE");
        System.out.println("SAVED ID: " + savedUser.getId());

        return savedUser;
    }
    public String loginUser(String email, String password) {

        System.out.println("LOGIN EMAIL: " + email);

        Optional<User> result = userRepository.findByEmail(email);

        System.out.println("USER FOUND: " + result.isPresent());

        if (result.isEmpty()) {
            return null;
        }

        User user = result.get();

        System.out.println("USER EMAIL FROM DB: " + user.getEmail());
        System.out.println("PASSWORD EXISTS: " +
                (user.getPassword() != null));

        boolean matched =
                passwordEncoder.matches(password, user.getPassword());

        System.out.println("PASSWORD MATCH: " + matched);

        if (!matched) {
            return null;
        }

        String token = jwtService.generateToken(user.getEmail());

        System.out.println("TOKEN GENERATED: " + token);

        return token;
    }

}
