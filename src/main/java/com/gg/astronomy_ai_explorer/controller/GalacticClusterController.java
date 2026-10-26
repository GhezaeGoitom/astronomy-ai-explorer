package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.GalacticClusterDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.GalacticClusterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/galactic-clusters")
@RequiredArgsConstructor
public class GalacticClusterController {

    private final GalacticClusterService galacticClusterService;

    @PostMapping
    public ResponseEntity<GalacticClusterDto> create(
            @Valid @RequestBody GalacticClusterDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(galacticClusterService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GalacticClusterDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                galacticClusterService.getById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<GalacticClusterDto>> getAll() {

        return ResponseEntity.ok(
                galacticClusterService.getAll()
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<GalacticClusterDto> update(
            @PathVariable Long id,
            @Valid @RequestBody GalacticClusterDto dto) {

        return ResponseEntity.ok(
                galacticClusterService.update(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        galacticClusterService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
