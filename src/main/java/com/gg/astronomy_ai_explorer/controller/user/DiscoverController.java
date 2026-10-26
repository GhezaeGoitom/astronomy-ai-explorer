package com.gg.astronomy_ai_explorer.controller.user;

import com.gg.astronomy_ai_explorer.dto.user.DiscoverDto;
import com.gg.astronomy_ai_explorer.service.user.DiscoverService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/discoveries")
public class DiscoverController {

    private final DiscoverService discoverService;

    public DiscoverController(
            DiscoverService discoverService) {

        this.discoverService = discoverService;
    }

    @GetMapping
    public ResponseEntity<List<DiscoverDto>> getAllDiscoveries() {

        return ResponseEntity.ok(
                discoverService.getAllDiscoveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscoverDto> getDiscoveryById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                discoverService.getDiscoveryById(id));
    }

    @PostMapping
    public ResponseEntity<DiscoverDto> createDiscovery(
            @Valid @RequestBody DiscoverDto discoverDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(discoverService.createDiscovery(
                        discoverDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscoverDto> updateDiscovery(
            @PathVariable Long id,
            @Valid @RequestBody DiscoverDto discoverDto) {

        return ResponseEntity.ok(
                discoverService.updateDiscovery(
                        id,
                        discoverDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDiscovery(
            @PathVariable Long id) {

        discoverService.deleteDiscovery(id);

        return ResponseEntity.noContent().build();
    }
}
