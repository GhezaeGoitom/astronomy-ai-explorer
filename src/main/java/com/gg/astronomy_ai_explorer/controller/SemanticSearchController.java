package com.gg.astronomy_ai_explorer.controller;

import com.gg.astronomy_ai_explorer.dto.embedding.SemanticSearchResultDto;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SemanticSearchController {

    private final DiscoveryEmbeddingService embeddingService;

    @GetMapping
    public List<SemanticSearchResultDto> search(
            @RequestParam String query,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return embeddingService.search(query, limit);
    }
}
