package com.example.preventduplicatelogin.service;

import com.example.preventduplicatelogin.domain.User;
import com.example.preventduplicatelogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findExistUser(Long seq) {
        return userRepository.findById(seq).orElse(null);
    }

    public User findUserByID(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }
}
