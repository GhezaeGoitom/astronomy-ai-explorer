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
public class DiscoverDto {

    private Long id;

    @NotNull(message = "Date discovered is required")
    private LocalDate dateDiscovered;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @Size(max = 100, message = "Coordinate must not exceed 100 characters")
    private String coordinate;

    private Long publishId;

    @NotNull(message = "Discover Id is required")
    private Long discoveredById;
}
