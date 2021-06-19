package com.epam.esm.auth.service;

import com.epam.esm.auth.util.UserDetailsConverter;
import com.epam.esm.entity.User;
import com.epam.esm.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserDetailsConverter converter;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(username);
        User user = optionalUser
                .orElseThrow(() -> new UsernameNotFoundException(" (username = " + username + ")"));
        return converter.convertToUserDetails(user);
    }
}
