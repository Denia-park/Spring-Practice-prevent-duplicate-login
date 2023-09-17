package com.example.preventduplicatelogin.login;

import com.example.preventduplicatelogin.domain.User;
import com.example.preventduplicatelogin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        User user = userService.findUserByID(username);

        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }

        final String encryptedPassword = passwordEncoder.encode(user.getPassword());

        return new UserDetailImpl(user.getUserId(), encryptedPassword);
    }
}
