package com.gg.astronomy_ai_explorer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniverseDto {

    private Long id;
    @NotBlank(message = "Universe name is required")
    @Size(max = 100, message = "Universe name must not exceed 100 characters")
    private String name;

    @Size(max = 5000, message = "Description must not exceed 5000 characters")
    private String description;

    @Size(max = 100, message = "Coordinate must not exceed 100 characters")
    private String coordinate;
}

//{
//        "id": 0,
//        "name": "solar sys",
//        "galaxyId": 1,
//        "description": "our solar sys ",
//        "discoverId": 1
//        }