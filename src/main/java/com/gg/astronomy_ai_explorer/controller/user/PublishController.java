package com.gg.astronomy_ai_explorer.controller.user;

import com.gg.astronomy_ai_explorer.dto.user.PublishDto;
import com.gg.astronomy_ai_explorer.service.user.PublishService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/publishes")
public class PublishController {

    private final PublishService publishService;

    public PublishController(PublishService publishService) {
        this.publishService = publishService;
    }

    @GetMapping
    public ResponseEntity<List<PublishDto>> getAllPublishes() {

        return ResponseEntity.ok(
                publishService.getAllPublishes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublishDto> getPublishById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                publishService.getPublishById(id));
    }

    @PostMapping
    public ResponseEntity<PublishDto> createPublish(
            @Valid @RequestBody PublishDto publishDto) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(publishService.createPublish(publishDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PublishDto> updatePublish(
            @PathVariable Long id,
            @Valid @RequestBody PublishDto publishDto) {

        return ResponseEntity.ok(
                publishService.updatePublish(
                        id,
                        publishDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublish(
            @PathVariable Long id) {

        publishService.deletePublish(id);

        return ResponseEntity.noContent().build();
    }
}
