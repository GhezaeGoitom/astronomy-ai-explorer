package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.GalacticSuperClusterDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.GalacticSuperClusterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galactic-superclusters")
public class GalacticSuperClusterController {

    private final GalacticSuperClusterService service;

    public GalacticSuperClusterController(
            GalacticSuperClusterService service) {

        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<GalacticSuperClusterDto>> getAll() {

        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalacticSuperClusterDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<GalacticSuperClusterDto> create(
            @Valid @RequestBody GalacticSuperClusterDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalacticSuperClusterDto> update(
            @PathVariable Long id,
            @Valid @RequestBody GalacticSuperClusterDto dto) {

        return ResponseEntity.ok(
                service.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
