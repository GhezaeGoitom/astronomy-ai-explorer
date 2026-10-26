package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.GalacticSuperClusterDto;
import com.gg.astronomy_ai_explorer.entity.GalacticSuperCluster;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.GalacticSuperClusterRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GalacticSuperClusterServiceImpl
        implements GalacticSuperClusterService {

    private final GalacticSuperClusterRepository repository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;




    @Override
    public List<GalacticSuperClusterDto> getAll() {

        return repository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public GalacticSuperClusterDto getById(Long id) {

        GalacticSuperCluster cluster = repository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Galactic supercluster not found: " + id, HttpStatus.NOT_FOUND));

        return toDto(cluster);
    }

    @Override
    public GalacticSuperClusterDto create(
            GalacticSuperClusterDto dto) {

        GalacticSuperCluster cluster =
                new GalacticSuperCluster();

        cluster.setName(dto.getName());
        cluster.setDescription(dto.getDescription());

        GalacticSuperCluster saved =
                repository.save(cluster);


        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALACTIC_SUPERCLUSTER, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.GALACTIC_SUPERCLUSTER,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public GalacticSuperClusterDto update(
            Long id,
            GalacticSuperClusterDto dto) {

        GalacticSuperCluster cluster = repository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Galactic supercluster not found: " + id, HttpStatus.NOT_FOUND));

        cluster.setName(dto.getName());
        cluster.setDescription(dto.getDescription());

        GalacticSuperCluster updated =
                repository.save(cluster);


        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.GALACTIC_SUPERCLUSTER, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.GALACTIC_SUPERCLUSTER,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        if (!repository.existsById(id)) {
            throw new ApiException(
                    "Galactic supercluster not found: " + id, HttpStatus.NOT_FOUND);
        }

        repository.deleteById(id);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.GALACTIC_SUPERCLUSTER,
                id
        );

        //
    }

    private GalacticSuperClusterDto toDto(
            GalacticSuperCluster cluster) {

        return new GalacticSuperClusterDto(
                cluster.getId(),
                cluster.getName(),
                cluster.getDescription()
        );
    }
}