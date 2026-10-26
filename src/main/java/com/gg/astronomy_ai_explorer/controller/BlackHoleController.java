package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.BlackHoleDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.BlackHoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/black-holes")
@RequiredArgsConstructor
public class BlackHoleController {

    private final BlackHoleService blackHoleService;

    @PostMapping
    public ResponseEntity<BlackHoleDto> create(
            @Valid @RequestBody BlackHoleDto dto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(blackHoleService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlackHoleDto> getById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                blackHoleService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<BlackHoleDto>> getAll() {

        return ResponseEntity.ok(
                blackHoleService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlackHoleDto> update(
            @PathVariable Long id,
            @Valid @RequestBody BlackHoleDto dto) {

        return ResponseEntity.ok(
                blackHoleService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id) {

        blackHoleService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
