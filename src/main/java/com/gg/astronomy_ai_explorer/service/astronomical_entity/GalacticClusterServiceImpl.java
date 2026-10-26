package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.GalacticClusterDto;
import com.gg.astronomy_ai_explorer.entity.GalacticCluster;
import com.gg.astronomy_ai_explorer.entity.GalacticSuperCluster;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.GalacticClusterRepository;
import com.gg.astronomy_ai_explorer.repository.GalacticSuperClusterRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalacticClusterServiceImpl implements GalacticClusterService {

    private final GalacticClusterRepository galacticClusterRepository;
    private final GalacticSuperClusterRepository galacticSuperClusterRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;


    @Override
    public GalacticClusterDto create(GalacticClusterDto dto) {

        GalacticCluster cluster = new GalacticCluster();

        cluster.setName(dto.getName());
        cluster.setDescription(dto.getDescription());

        if (dto.getGalacticSuperClusterId() != null) {

            GalacticSuperCluster superCluster =
                    galacticSuperClusterRepository.findById(
                            dto.getGalacticSuperClusterId()
                    ).orElseThrow(() ->
                            new ApiException(
                                    "Galactic supercluster not found with id: "
                                            + dto.getGalacticSuperClusterId()
                                    , HttpStatus.NOT_FOUND)
                    );

            cluster.setGalacticSuperCluster(superCluster);
        }

        GalacticCluster saved =
                galacticClusterRepository.save(cluster);


        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALACTIC_CLUSTER, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.GALACTIC_CLUSTER,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public GalacticClusterDto getById(Long id) {

        GalacticCluster cluster =
                galacticClusterRepository.findById(id)
                        .orElseThrow(() ->
                                new ApiException(
                                        "Galactic cluster not found with id: " + id, HttpStatus.NOT_FOUND
                                )
                        );

        return toDto(cluster);
    }

    @Override
    public List<GalacticClusterDto> getAll() {

        return galacticClusterRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public GalacticClusterDto update(Long id, GalacticClusterDto dto) {

        GalacticCluster cluster =
                galacticClusterRepository.findById(id)
                        .orElseThrow(() ->
                                new ApiException(
                                        "Galactic cluster not found with id: " + id
                                        , HttpStatus.NOT_FOUND)
                        );

        cluster.setName(dto.getName());
        cluster.setDescription(dto.getDescription());

        if (dto.getGalacticSuperClusterId() != null) {

            GalacticSuperCluster superCluster =
                    galacticSuperClusterRepository.findById(
                            dto.getGalacticSuperClusterId()
                    ).orElseThrow(() ->
                            new ApiException(
                                    "Galactic supercluster not found with id: "
                                            + dto.getGalacticSuperClusterId()
                                    , HttpStatus.NOT_FOUND)
                    );

            cluster.setGalacticSuperCluster(superCluster);

        } else {
            cluster.setGalacticSuperCluster(null);
        }

        GalacticCluster updated =
                galacticClusterRepository.save(cluster);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALACTIC_CLUSTER, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.GALACTIC_CLUSTER,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        if (!galacticClusterRepository.existsById(id)) {
            throw new ApiException(
                    "Galactic cluster not found with id: " + id
                    , HttpStatus.NOT_FOUND);
        }

        galacticClusterRepository.deleteById(id);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.GALACTIC_CLUSTER,
                id
        );

        //
    }

    private GalacticClusterDto toDto(GalacticCluster cluster) {

        Long superClusterId = null;

        if (cluster.getGalacticSuperCluster() != null) {
            superClusterId =
                    cluster.getGalacticSuperCluster().getId();
        }

        return new GalacticClusterDto(
                cluster.getId(),
                cluster.getName(),
                superClusterId,
                cluster.getDescription()
        );
    }
}
