package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.GalaxyDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.GalaxyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/galaxies")
@RequiredArgsConstructor
public class GalaxyController {

    private final GalaxyService galaxyService;

    @PostMapping
    public ResponseEntity<GalaxyDto> create(
            @Valid @RequestBody GalaxyDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(galaxyService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<GalaxyDto>> getAll() {

        return ResponseEntity.ok(
                galaxyService.getAll()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalaxyDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                galaxyService.getById(id)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalaxyDto> update(
            @PathVariable Long id,
            @Valid @RequestBody GalaxyDto dto) {

        return ResponseEntity.ok(
                galaxyService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        galaxyService.delete(id);

        return ResponseEntity.noContent().build();
    }
}