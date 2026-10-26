package com.gg.astronomy_ai_explorer.repository.embedding;

import com.gg.astronomy_ai_explorer.dto.embedding.SemanticSearchResultDto;
import com.gg.astronomy_ai_explorer.entity.embedding.DiscoveryEmbedding;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DiscoveryEmbeddingRepository
        extends JpaRepository<DiscoveryEmbedding, Long> {

    Optional<DiscoveryEmbedding> findByEntityTypeAndEntityId(
            String entityType,
            Long entityId
    );

    @Query(value = """
    SELECT *
    FROM discovery_embedding
    ORDER BY embedding <=> CAST(:embedding AS vector)
    LIMIT :limitCount
    """, nativeQuery = true)
    List<DiscoveryEmbedding> searchSimilar(
            @Param("embedding") String embedding,
            @Param("limitCount") int limitCount
    );

    void deleteByEntityTypeAndEntityId(
            String entityType,
            Long entityId
    );
}