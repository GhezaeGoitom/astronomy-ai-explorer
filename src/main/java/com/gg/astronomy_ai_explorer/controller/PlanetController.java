package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.PlanetDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.PlanetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/planets")
@RequiredArgsConstructor
public class PlanetController {

    private final PlanetService planetService;

    @PostMapping
    public ResponseEntity<PlanetDto> create(@Valid @RequestBody PlanetDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(planetService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlanetDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(planetService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<PlanetDto>> getAll() {
        return ResponseEntity.ok(planetService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlanetDto> update(
            @PathVariable Long id,
            @Valid  @RequestBody PlanetDto dto) {

        return ResponseEntity.ok(planetService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        planetService.delete(id);

        return ResponseEntity.noContent().build();
    }
}