package com.gg.astronomy_ai_explorer.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    List<String> roles;
}