package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.UniverseDto;
import com.gg.astronomy_ai_explorer.entity.Universe;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.UniverseRepository;
import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UniverseServiceImpl implements UniverseService {

    private final UniverseRepository universeRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;


    @Override
    public List<UniverseDto> getAll() {
        return universeRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public UniverseDto getById(Long id) {
        Universe universe = universeRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Universe not found: " + id, HttpStatus.NOT_FOUND));

        return toDto(universe);
    }

    @Override
    public UniverseDto create(UniverseDto dto) {

        Universe universe = new Universe();

        universe.setName(dto.getName());
        universe.setDescription(dto.getDescription());
        universe.setCoordinate(dto.getCoordinate());

        Universe saved = universeRepository.save(universe);

        // create embedding

        String content = discoveryContentBuilder.buildContent(EntityType.UNIVERSE, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.UNIVERSE,
                universe.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public UniverseDto update(Long id, UniverseDto dto) {

        Universe universe = universeRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Universe not found: " + id, HttpStatus.NOT_FOUND));

        universe.setName(dto.getName());
        universe.setDescription(dto.getDescription());
        universe.setCoordinate(dto.getCoordinate());

        Universe updated = universeRepository.save(universe);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.UNIVERSE, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.UNIVERSE,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        if (!universeRepository.existsById(id)) {
            throw new ApiException("Universe not found: " + id, HttpStatus.NOT_FOUND);
        }

        universeRepository.deleteById(id);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.UNIVERSE,
                id
        );

        //
    }

    private UniverseDto toDto(Universe universe) {
        return new UniverseDto(
                universe.getId(),
                universe.getName(),
                universe.getDescription(),
                universe.getCoordinate()
        );
    }
}
