package com.gg.astronomy_ai_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolarSystemDto {

    private Long id;

    @NotBlank(message = "Solar system name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private Long galaxyId;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @NotNull(message = "Discover id is required")
    private Long discoverId;
}
