package com.gg.astronomy_ai_explorer.component.embedding;


import com.gg.astronomy_ai_explorer.entity.*;
import com.gg.astronomy_ai_explorer.util.EntityType;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryContentBuilderImpl implements DiscoveryContentBuilder {

    @Override
    public String buildContent(EntityType entityType, Object entity) {

        return switch (entityType) {

            case EntityType.PLANET ->
                    buildPlanetContent((Planet) entity);

            case EntityType.STAR ->
                    buildStarContent((Star) entity);

            case EntityType.GALAXY ->
                    buildGalaxyContent((Galaxy) entity);

            case EntityType.SUPERNOVA ->
                    buildSupernovaContent((Supernova) entity);

            case EntityType.BLACK_HOLE ->
                    buildBlackholeContent((BlackHole) entity);

            case EntityType.UNIVERSE ->
                    buildUniverseContent((Universe) entity);

            case EntityType.GALACTIC_CLUSTER ->
                    buildGalacticClusterContent((GalacticCluster) entity);

            case EntityType.SOLAR_SYSTEM ->
                    buildSolarSystem((SolarSystem) entity);

            case EntityType.GALACTIC_SUPERCLUSTER ->
                    buildGalacticSuperclusterContent(
                            (GalacticSuperCluster) entity
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Unsupported entity type: " + entityType
                    );
        };
    }

    private String buildPlanetContent(Planet entity) {
        return """
            Planet: %s
            Description: %s
            Object size: %s
            Object mass: %s
            Score: %s
            Solar system: %s
            Discovered by: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getObjectSize(),
                entity.getObjectMass(),
                entity.getScore(),
                entity.getSolarSystem() != null
                        ? entity.getSolarSystem().getName()
                        : "Unknown",
                entity.getDiscover() != null
                        ? entity.getDiscover().getDiscoveredBy().getFirstname() + " " + entity.getDiscover().getDiscoveredBy().getLastname()
                        : "Unknown"
        );
    }

    private String buildStarContent(Star entity) {
        return """
            Star: %s
            Description: %s
            Object size: %s
            Object mass: %s
            Solar system: %s
            Discovered by: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getObjectSize(),
                entity.getObjectMass(),
                entity.getSolarSystem() != null
                        ? entity.getSolarSystem().getName()
                        : "Unknown",
                entity.getDiscover() != null
                        ? entity.getDiscover().getDiscoveredBy().getFirstname() + " " + entity.getDiscover().getDiscoveredBy().getLastname()
                        : "Unknown"
        );
    }

    private String buildGalaxyContent(Galaxy entity) {
        return """
            Galaxy: %s
            Description: %s
            Universe: %s
            Galactic cluster: %s
            Discovered by: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getUniverse() != null
                        ? entity.getUniverse().getName()
                        : "Unknown",
                entity.getGalacticCluster() != null
                        ? entity.getGalacticCluster().getName()
                        : "Unknown",
                entity.getDiscover() != null
                        ? entity.getDiscover().getDiscoveredBy().getFirstname() + " " + entity.getDiscover().getDiscoveredBy().getLastname()
                        : "Unknown"
        );
    }

    private String buildSolarSystem(SolarSystem entity) {
        return """
            Solar_System: %s
            Description: %s
            Galaxy: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getGalaxy() != null
                        ? entity.getGalaxy().getName()
                        : "Unknown"
        );
    }

    private String buildSupernovaContent(Supernova entity) {
        return """
            Supernova: %s
            Description: %s
            Brightness: %s
            Universe: %s
            Discovered by: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getBrightness(),
                entity.getUniverse() != null
                        ? entity.getUniverse().getName()
                        : "Unknown",
                entity.getDiscover() != null
                        ? entity.getDiscover().getDiscoveredBy().getFirstname() + " " + entity.getDiscover().getDiscoveredBy().getLastname()
                        : "Unknown"
        );
    }

    private String buildBlackholeContent(BlackHole entity) {
        return """
            Black hole: %s
            Description: %s
            Object size: %s
            Object mass: %s
            Galaxy: %s
            Discovered by: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getObjectSize(),
                entity.getObjectMass(),
                entity.getGalaxy() != null
                        ? entity.getGalaxy().getName()
                        : "Unknown",
                entity.getDiscover() != null
                        ? entity.getDiscover().getDiscoveredBy().getFirstname() + " " + entity.getDiscover().getDiscoveredBy().getLastname()
                        : "Unknown"
        );
    }

    private String buildUniverseContent(Universe entity) {
        return """
            Universe: %s
            Description: %s
            """.formatted(
                entity.getName(),
                entity.getDescription()
        );
    }

    private String buildGalacticClusterContent(GalacticCluster entity) {
        return """
            Galactic cluster: %s
            Description: %s
            Galactic supercluster: %s
            """.formatted(
                entity.getName(),
                entity.getDescription(),
                entity.getGalacticSuperCluster() != null
                        ? entity.getGalacticSuperCluster().getName()
                        : "Unknown"
        );
    }

    private String buildGalacticSuperclusterContent(
            GalacticSuperCluster entity
    ) {
        return """
            Galactic supercluster: %s
            Description: %s
            """.formatted(
                entity.getName(),
                entity.getDescription()
        );
    }
}
