package com.easymoney.service;

import com.easymoney.model.User;
import com.easymoney.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder(); // pour sécuriser les mdp
    }

    public User createUser(String username, String email, String rawPassword, String phone, int age) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email déjà utilisé.");
        }
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Nom d'utilisateur déjà utilisé.");
        }

        String hashedPassword = passwordEncoder.encode(rawPassword);

        User user = new User(username, hashedPassword, email, phone, age);
        return userRepository.save(user);
    }

    public User getUserById(String uuid) {
        return userRepository.findById(uuid)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé."));
    }

    public boolean checkUserExistByUuid(String uuid) {
        return userRepository.existsById(uuid);
    }
}