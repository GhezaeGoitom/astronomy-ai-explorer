package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.UniverseDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.UniverseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/universes")
public class UniverseController {

    private final UniverseService universeService;

    public UniverseController(UniverseService universeService) {
        this.universeService = universeService;
    }

    @GetMapping
    public ResponseEntity<List<UniverseDto>> getAll() {
        return ResponseEntity.ok(universeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UniverseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(universeService.getById(id));
    }

    @PostMapping
    public ResponseEntity<UniverseDto> create(
            @Valid @RequestBody UniverseDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(universeService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniverseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody UniverseDto dto) {

        return ResponseEntity.ok(
                universeService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        universeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
