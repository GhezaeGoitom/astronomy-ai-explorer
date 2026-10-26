package com.gg.astronomy_ai_explorer.controller;
import com.gg.astronomy_ai_explorer.dto.SolarSystemDto;
import com.gg.astronomy_ai_explorer.service.astronomical_entity.SolarSystemService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/solar-systems")
public class SolarSystemController {

    private final SolarSystemService solarSystemService;

    public SolarSystemController(SolarSystemService solarSystemService) {
        this.solarSystemService = solarSystemService;
    }

    @GetMapping
    public ResponseEntity<List<SolarSystemDto>> getAllSolarSystems() {
        return ResponseEntity.ok(solarSystemService.getAllSolarSystems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolarSystemDto> getSolarSystemById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                solarSystemService.getSolarSystemById(id)
        );
    }

    @PostMapping
    public ResponseEntity<SolarSystemDto> createSolarSystem(
            @Valid @RequestBody SolarSystemDto solarSystemDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(solarSystemService.createSolarSystem(solarSystemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SolarSystemDto> updateSolarSystem(
            @PathVariable Long id,
            @Valid @RequestBody SolarSystemDto solarSystemDto) {

        return ResponseEntity.ok(
                solarSystemService.updateSolarSystem(id, solarSystemDto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSolarSystem(
            @PathVariable Long id) {

        solarSystemService.deleteSolarSystem(id);

        return ResponseEntity.noContent().build();
    }
}
