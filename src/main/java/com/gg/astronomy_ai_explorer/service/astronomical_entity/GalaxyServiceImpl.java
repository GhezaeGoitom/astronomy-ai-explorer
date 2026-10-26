package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.GalaxyDto;
import com.gg.astronomy_ai_explorer.entity.GalacticCluster;
import com.gg.astronomy_ai_explorer.entity.Galaxy;
import com.gg.astronomy_ai_explorer.entity.Universe;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.GalacticClusterRepository;
import com.gg.astronomy_ai_explorer.repository.GalaxyRepository;
import com.gg.astronomy_ai_explorer.repository.UniverseRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalaxyServiceImpl implements GalaxyService {

    private final GalaxyRepository galaxyRepository;
    private final UniverseRepository universeRepository;
    private final GalacticClusterRepository galacticClusterRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;


    @Override
    public GalaxyDto create(GalaxyDto dto) {

        Galaxy galaxy = new Galaxy();

        galaxy.setName(dto.getName());
        galaxy.setDescription(dto.getDescription());

        if (dto.getUniverseId() != null) {

            Universe universe = universeRepository
                    .findById(dto.getUniverseId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Universe not found with id: "
                                            + dto.getUniverseId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setUniverse(universe);
        }

        if (dto.getGalacticClusterId() != null) {

            GalacticCluster cluster = galacticClusterRepository
                    .findById(dto.getGalacticClusterId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galactic cluster not found with id: "
                                            + dto.getGalacticClusterId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setGalacticCluster(cluster);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository
                    .findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discovery not found with id: "
                                            + dto.getDiscoverId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setDiscover(discover);
        }

        Galaxy saved = galaxyRepository.save(galaxy);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALAXY, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.GALAXY,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public List<GalaxyDto> getAll() {

        return galaxyRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public GalaxyDto getById(Long id) {

        Galaxy galaxy = galaxyRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Galaxy not found with id: " + id
                                , HttpStatus.NOT_FOUND));

        return toDto(galaxy);
    }

    @Override
    public GalaxyDto update(Long id, GalaxyDto dto) {

        Galaxy galaxy = galaxyRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Galaxy not found with id: " + id
                                , HttpStatus.NOT_FOUND));

        galaxy.setName(dto.getName());
        galaxy.setDescription(dto.getDescription());

        if (dto.getUniverseId() != null) {

            Universe universe = universeRepository
                    .findById(dto.getUniverseId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Universe not found with id: "
                                            + dto.getUniverseId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setUniverse(universe);

        } else {
            galaxy.setUniverse(null);
        }

        if (dto.getGalacticClusterId() != null) {

            GalacticCluster cluster = galacticClusterRepository
                    .findById(dto.getGalacticClusterId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galactic cluster not found with id: "
                                            + dto.getGalacticClusterId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setGalacticCluster(cluster);

        } else {
            galaxy.setGalacticCluster(null);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository
                    .findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discovery not found with id: "
                                            + dto.getDiscoverId()
                                    , HttpStatus.NOT_FOUND));

            galaxy.setDiscover(discover);

        } else {
            galaxy.setDiscover(null);
        }

        Galaxy updated = galaxyRepository.save(galaxy);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALAXY, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.GALAXY,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        Galaxy galaxy = galaxyRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Galaxy not found with id: " + id
                                , HttpStatus.NOT_FOUND));

        galaxyRepository.delete(galaxy);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.GALAXY,
                id
        );

        //
    }

    private GalaxyDto toDto(Galaxy galaxy) {

        GalaxyDto dto = new GalaxyDto();

        dto.setId(galaxy.getId());
        dto.setName(galaxy.getName());
        dto.setDescription(galaxy.getDescription());

        dto.setUniverseId(
                galaxy.getUniverse() != null
                        ? galaxy.getUniverse().getId()
                        : null
        );

        dto.setGalacticClusterId(
                galaxy.getGalacticCluster() != null
                        ? galaxy.getGalacticCluster().getId()
                        : null
        );

        dto.setDiscoverId(
                galaxy.getDiscover() != null
                        ? galaxy.getDiscover().getId()
                        : null
        );

        return dto;
    }
}
