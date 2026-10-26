ALTER TABLE discovery_embedding
DROP CONSTRAINT chk_discovery_embedding_entity_type;

UPDATE discovery_embedding
SET entity_type = 'BLACK_HOLE'
WHERE entity_type = 'BLACKHOLE';

ALTER TABLE discovery_embedding
    ADD CONSTRAINT chk_discovery_embedding_entity_type
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
                            'BLACK_HOLE'
                )
            );