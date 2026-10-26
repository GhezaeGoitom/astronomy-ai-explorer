package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.SupernovaDto;
import com.gg.astronomy_ai_explorer.entity.Supernova;
import com.gg.astronomy_ai_explorer.entity.Universe;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.SupernovaRepository;
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
public class SupernovaServiceImpl implements SupernovaService {

    private final SupernovaRepository supernovaRepository;
    private final UniverseRepository universeRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;



    @Override
    public SupernovaDto create(SupernovaDto dto) {

        Supernova supernova = new Supernova();

        supernova.setName(dto.getName());
        supernova.setBrightness(dto.getBrightness());
        supernova.setDescription(dto.getDescription());

        // Set Universe relationship
        if (dto.getUniverseId() != null) {

            Universe universe = universeRepository.findById(dto.getUniverseId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Universe not found with id: "
                                            + dto.getUniverseId(), HttpStatus.NOT_FOUND));

            supernova.setUniverse(universe);
        }

        // Set Discover relationship
        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            supernova.setDiscover(discover);
        }

        Supernova saved = supernovaRepository.save(supernova);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.SUPERNOVA, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.SUPERNOVA,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public SupernovaDto getById(Long id) {

        Supernova supernova = supernovaRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Supernova not found with id: " + id, HttpStatus.NOT_FOUND));

        return toDto(supernova);
    }

    @Override
    public List<SupernovaDto> getAll() {

        return supernovaRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public SupernovaDto update(Long id, SupernovaDto dto) {

        Supernova supernova = supernovaRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Supernova not found with id: " + id, HttpStatus.NOT_FOUND));

        supernova.setName(dto.getName());
        supernova.setBrightness(dto.getBrightness());
        supernova.setDescription(dto.getDescription());

        // Update Universe relationship
        if (dto.getUniverseId() != null) {

            Universe universe = universeRepository.findById(dto.getUniverseId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Universe not found with id: "
                                            + dto.getUniverseId(), HttpStatus.NOT_FOUND));

            supernova.setUniverse(universe);

        } else {
            supernova.setUniverse(null);
        }

        // Update Discover relationship
        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            supernova.setDiscover(discover);

        } else {
            supernova.setDiscover(null);
        }

        Supernova updated = supernovaRepository.save(supernova);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.SUPERNOVA, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.SUPERNOVA,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        Supernova supernova = supernovaRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Supernova not found with id: " + id, HttpStatus.NOT_FOUND));

        supernovaRepository.delete(supernova);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.SUPERNOVA,
                id
        );

        //
    }

    private SupernovaDto toDto(Supernova supernova) {

        SupernovaDto dto = new SupernovaDto();

        dto.setId(supernova.getId());
        dto.setName(supernova.getName());
        dto.setBrightness(supernova.getBrightness());
        dto.setDescription(supernova.getDescription());

        if (supernova.getUniverse() != null) {
            dto.setUniverseId(
                    supernova.getUniverse().getId());
        }

        if (supernova.getDiscover() != null) {
            dto.setDiscoverId(
                    supernova.getDiscover().getId());
        }

        return dto;
    }
}