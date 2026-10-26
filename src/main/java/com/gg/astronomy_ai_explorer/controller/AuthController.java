
package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.auth.LoginRequest;
import com.gg.astronomy_ai_explorer.dto.auth.LoginResponse;
import com.gg.astronomy_ai_explorer.dto.user.AppUserDto;
import com.gg.astronomy_ai_explorer.dto.user.AppUserResponseDto;
import com.gg.astronomy_ai_explorer.service.security.AuthService;
import com.gg.astronomy_ai_explorer.service.user.AppUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {


    private final AppUserService appUserService;
    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request
    ) {
        return authService.login(request);
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserResponseDto> createUser(
            @Valid @RequestBody AppUserDto appUserDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(appUserService.createUser(appUserDto));
    }
}
