package com.gg.astronomy_ai_explorer.entity.embedding;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;
import lombok.*;


@Entity
@Table(
        name = "discovery_embedding",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_discovery_embedding_entity",
                        columnNames = {"entity_type", "entity_id"}
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscoveryEmbedding {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entity_type", nullable = false, length = 30)
    private String entityType;

    @Column(name = "entity_id", nullable = false)
    private Long entityId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    @JdbcTypeCode(SqlTypes.VECTOR)
    @org.hibernate.annotations.Array(length = 384)
    private float[] embedding;


    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
