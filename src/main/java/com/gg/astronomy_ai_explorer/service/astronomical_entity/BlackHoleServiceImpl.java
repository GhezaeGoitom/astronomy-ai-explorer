package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.BlackHoleDto;
import com.gg.astronomy_ai_explorer.entity.BlackHole;
import com.gg.astronomy_ai_explorer.entity.Galaxy;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.BlackHoleRepository;
import com.gg.astronomy_ai_explorer.repository.GalaxyRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlackHoleServiceImpl implements BlackHoleService {

    private final BlackHoleRepository blackHoleRepository;
    private final GalaxyRepository galaxyRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;


    @Override
    public BlackHoleDto create(BlackHoleDto dto) {

        BlackHole blackHole = new BlackHole();

        blackHole.setName(dto.getName());
        blackHole.setObjectSize(dto.getObjectSize());
        blackHole.setObjectMass(dto.getObjectMass());
        blackHole.setDescription(dto.getDescription());

        if (dto.getGalaxyId() != null) {

            Galaxy galaxy = galaxyRepository.findById(dto.getGalaxyId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galaxy not found with id: "
                                            + dto.getGalaxyId(), HttpStatus.NOT_FOUND));

            blackHole.setGalaxy(galaxy);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            blackHole.setDiscover(discover);
        }

        BlackHole saved = blackHoleRepository.save(blackHole);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.BLACK_HOLE, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.BLACK_HOLE,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public BlackHoleDto getById(Long id) {

        BlackHole blackHole = blackHoleRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Black hole not found with id: " + id, HttpStatus.NOT_FOUND));

        return toDto(blackHole);
    }

    @Override
    public List<BlackHoleDto> getAll() {

        return blackHoleRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public BlackHoleDto update(Long id, BlackHoleDto dto) {

        BlackHole blackHole = blackHoleRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Black hole not found with id: " + id, HttpStatus.NOT_FOUND));

        blackHole.setName(dto.getName());
        blackHole.setObjectSize(dto.getObjectSize());
        blackHole.setObjectMass(dto.getObjectMass());
        blackHole.setDescription(dto.getDescription());

        if (dto.getGalaxyId() != null) {

            Galaxy galaxy = galaxyRepository.findById(dto.getGalaxyId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galaxy not found with id: "
                                            + dto.getGalaxyId(), HttpStatus.NOT_FOUND));

            blackHole.setGalaxy(galaxy);

        } else {
            blackHole.setGalaxy(null);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            blackHole.setDiscover(discover);

        } else {
            blackHole.setDiscover(null);
        }

        BlackHole updated = blackHoleRepository.save(blackHole);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.BLACK_HOLE, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.BLACK_HOLE,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        BlackHole blackHole = blackHoleRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Black hole not found with id: " + id, HttpStatus.NOT_FOUND));

        blackHoleRepository.delete(blackHole);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.BLACK_HOLE,
                id
        );

        //
    }

    private BlackHoleDto toDto(BlackHole blackHole) {

        BlackHoleDto dto = new BlackHoleDto();

        dto.setId(blackHole.getId());
        dto.setName(blackHole.getName());
        dto.setObjectSize(blackHole.getObjectSize());
        dto.setObjectMass(blackHole.getObjectMass());
        dto.setDescription(blackHole.getDescription());

        if (blackHole.getGalaxy() != null) {
            dto.setGalaxyId(
                    blackHole.getGalaxy().getId());
        }

        if (blackHole.getDiscover() != null) {
            dto.setDiscoverId(
                    blackHole.getDiscover().getId());
        }

        return dto;
    }
}
