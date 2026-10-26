package com.gg.astronomy_ai_explorer.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppUserResponseDto {

    private Long id;

    private String username;

    private String email;

    private Boolean enabled;

    private Set<String> roles;

    private Boolean hasPersonProfile;

    private LocalDateTime createdAt;
}