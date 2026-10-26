package com.gg.astronomy_ai_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalacticClusterDto {

    private Long id;

    @NotBlank(message = "Galactic cluster name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    private Long galacticSuperClusterId;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;
}
