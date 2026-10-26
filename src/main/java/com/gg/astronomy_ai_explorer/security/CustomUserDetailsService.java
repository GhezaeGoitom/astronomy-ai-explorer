package com.gg.astronomy_ai_explorer.security;


import com.gg.astronomy_ai_explorer.entity.user.AppRole;
import com.gg.astronomy_ai_explorer.entity.user.AppUser;
import com.gg.astronomy_ai_explorer.repository.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        AppUser user = appUserRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found: " + username
                        )
                );

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .disabled(!user.getEnabled())
                .authorities(
                        user.getRoles()
                                .stream()
                                .map(AppRole::getName)
                                .toArray(String[]::new)
                )
                .build();
    }
}