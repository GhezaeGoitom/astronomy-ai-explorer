package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.SupernovaDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.SupernovaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supernovae")
@RequiredArgsConstructor
public class SupernovaController {

    private final SupernovaService supernovaService;

    @PostMapping
    public ResponseEntity<SupernovaDto> create(
            @Valid @RequestBody SupernovaDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(supernovaService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupernovaDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                supernovaService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupernovaDto>> getAll() {

        return ResponseEntity.ok(
                supernovaService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupernovaDto> update(
            @PathVariable Long id,
            @Valid @RequestBody SupernovaDto dto) {

        return ResponseEntity.ok(
                supernovaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        supernovaService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
