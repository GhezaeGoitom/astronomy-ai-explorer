package com.gg.astronomy_ai_explorer.service.embedding;

import com.gg.astronomy_ai_explorer.dto.embedding.SemanticSearchResultDto;
import com.gg.astronomy_ai_explorer.entity.embedding.DiscoveryEmbedding;
import com.gg.astronomy_ai_explorer.util.EntityType;

import java.util.List;
import java.util.Optional;

public interface DiscoveryEmbeddingService {

    void createEmbedding(
            EntityType entityType,
            Long entityId,
            String content
    );

        void updateEmbedding(
                EntityType entityType,
                Long entityId,
                String content
        );

        void deleteEmbedding(
                EntityType entityType,
                Long entityId
        );

        List<SemanticSearchResultDto> search(
                String query,
                int limit
        );


}
