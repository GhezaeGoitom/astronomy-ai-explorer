package com.gg.astronomy_ai_explorer.service.security;

import com.gg.astronomy_ai_explorer.dto.auth.LoginRequest;
import com.gg.astronomy_ai_explorer.dto.auth.LoginResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    LoginResponse login(LoginRequest request);

}