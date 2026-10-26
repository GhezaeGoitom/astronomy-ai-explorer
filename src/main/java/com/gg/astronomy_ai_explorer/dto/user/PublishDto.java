package com.gg.astronomy_ai_explorer.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublishDto {

    private Long id;

    @NotNull(message = "Date of publication is required")
    private LocalDate datePublished;

    @Size(max = 100, message = "Place must not exceed 100 characters")
    private String place;
}
