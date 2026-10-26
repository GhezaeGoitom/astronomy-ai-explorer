package com.gg.astronomy_ai_explorer.service.embedding;

import com.gg.astronomy_ai_explorer.dto.embedding.SemanticSearchResultDto;
import com.gg.astronomy_ai_explorer.entity.embedding.DiscoveryEmbedding;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.embedding.DiscoveryEmbeddingRepository;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DiscoveryEmbeddingServiceImpl
        implements DiscoveryEmbeddingService {

    private final DiscoveryEmbeddingRepository discoveryEmbeddingRepository;
    private final EmbeddingService embeddingService;

    @Override
    public void createEmbedding(
            EntityType entityType,
            Long entityId,
            String content
    ) {

         float[] embedding =
                embeddingService.generateEmbedding(content);

        DiscoveryEmbedding discoveryEmbedding =
                new DiscoveryEmbedding();

        discoveryEmbedding.setEntityType(entityType.name());
        discoveryEmbedding.setEntityId(entityId);
        discoveryEmbedding.setContent(content);
        discoveryEmbedding.setEmbedding(embedding);
        discoveryEmbedding.setCreatedAt(LocalDateTime.now());

        discoveryEmbeddingRepository.save(discoveryEmbedding);
    }

    @Override
    public void updateEmbedding(
            EntityType entityType,
            Long entityId,
            String content
    ) {

        DiscoveryEmbedding discoveryEmbedding =
                discoveryEmbeddingRepository
                        .findByEntityTypeAndEntityId(
                                entityType.name(),
                                entityId
                        )
                        .orElseThrow(() ->
                                new ApiException(
                                        "Embedding not found for "
                                                + entityType, HttpStatus.NOT_FOUND
                                )
                        );

        float[] embedding =
                embeddingService.generateEmbedding(content);

        discoveryEmbedding.setContent(content);
        discoveryEmbedding.setEmbedding(embedding);
        discoveryEmbedding.setCreatedAt(LocalDateTime.now());

        discoveryEmbeddingRepository.save(discoveryEmbedding);
    }

    @Override
    public void deleteEmbedding(
            EntityType entityType,
            Long entityId
    ) {

        discoveryEmbeddingRepository.deleteByEntityTypeAndEntityId(
                entityType.name(),
                entityId
        );
    }


    @Override
    public List<SemanticSearchResultDto> search(String query, int limit) {

        float[] queryEmbedding =
                embeddingService.generateEmbedding(query);

        String vector = toVectorString(queryEmbedding);

        return discoveryEmbeddingRepository
                .searchSimilar(vector, limit)
                .stream()
                .map(entity -> new SemanticSearchResultDto(
                        entity.getEntityType(),
                        entity.getEntityId(),
                        entity.getContent()
                ))
                .toList();
    }

    private String toVectorString(float[] embedding) {

        StringBuilder vector = new StringBuilder("[");

        for (int i = 0; i < embedding.length; i++) {
            if (i > 0) {
                vector.append(",");
            }

            vector.append(embedding[i]);
        }

        vector.append("]");

        return vector.toString();
    }

}
