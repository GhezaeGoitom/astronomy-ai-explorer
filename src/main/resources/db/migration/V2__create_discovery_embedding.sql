-- ============================================================
-- 1. EXTENSIONS
-- ============================================================

CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE discovery_embedding (
                                     id           BIGSERIAL PRIMARY KEY,
                                     entity_type  VARCHAR(30) NOT NULL,
                                     entity_id    BIGINT      NOT NULL,
                                     content      TEXT        NOT NULL,
                                     embedding    vector(384) NOT NULL,
                                     created_at   TIMESTAMP   NOT NULL DEFAULT now(),

                                     CONSTRAINT chk_discovery_embedding_entity_type
                                         CHECK (
                                             entity_type IN (
                                                             'PLANET',
                                                             'GALACTIC_CLUSTER',
                                                             'GALACTIC_SUPERCLUSTER',
                                                             'SOLAR_SYSTEM',
                                                             'UNIVERSE',
                                                             'STAR',
                                                             'GALAXY',
                                                             'SUPERNOVA',
                                                             'BLACKHOLE'
                                                 )
                                             ),

                                     CONSTRAINT uq_discovery_embedding_entity
                                         UNIQUE (entity_type, entity_id)
);

CREATE INDEX idx_discovery_embedding_vector
    ON discovery_embedding
        USING hnsw (embedding vector_cosine_ops);