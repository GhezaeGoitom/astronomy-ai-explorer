package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.StarDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.StarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stars")
@RequiredArgsConstructor
public class StarController {

    private final StarService starService;

    @PostMapping
    public ResponseEntity<StarDto> create(@Valid @RequestBody StarDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(starService.create(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StarDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(starService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StarDto>> getAll() {
        return ResponseEntity.ok(starService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<StarDto> update(
            @PathVariable Long id,
            @Valid @RequestBody StarDto dto) {

        return ResponseEntity.ok(starService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        starService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
