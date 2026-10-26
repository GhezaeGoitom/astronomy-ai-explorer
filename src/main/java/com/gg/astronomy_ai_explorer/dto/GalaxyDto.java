package com.gg.astronomy_ai_explorer.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalaxyDto {

    private Long id;

    @NotBlank(message = "Galaxy name is required")
    @Size(max = 100, message = "Galaxy name must not exceed 100 characters")
    private String name;

    @NotNull(message = "Universe id is required")
    private Long universeId;

    private Long galacticClusterId;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @NotNull(message = "Discover id is required")
    private Long discoverId;
}
