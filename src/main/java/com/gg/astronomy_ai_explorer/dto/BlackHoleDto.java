package com.gg.astronomy_ai_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlackHoleDto {

    private Long id;
    @NotBlank(message = "Black hole name is required")
    @Size(max = 100, message = "Name must not exceed 100 characters")
    private String name;

    @Positive(message = "Object size must be positive")
    private Long objectSize;

    @Positive(message = "Object mass must be positive")
    private Long objectMass;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    private Long galaxyId;

    private Long discoverId;
}
