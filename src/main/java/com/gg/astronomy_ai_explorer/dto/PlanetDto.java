package com.gg.astronomy_ai_explorer.dto;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanetDto {

    private Long id;

    @NotBlank(message = "Planet name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Positive(message = "Object size must be positive")
    private Long objectSize;

    @Positive(message = "Object mass must be positive")
    private Long objectMass;

    @PositiveOrZero(message = "Score cannot be negative")
    private Integer score;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    private Long solarSystemId;

    @NotNull(message = "Discover id is required")
    private Long discoverId;
}
