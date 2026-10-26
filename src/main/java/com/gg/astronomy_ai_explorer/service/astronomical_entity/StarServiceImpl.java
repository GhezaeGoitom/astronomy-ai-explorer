package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.StarDto;
import com.gg.astronomy_ai_explorer.entity.SolarSystem;
import com.gg.astronomy_ai_explorer.entity.Star;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.SolarSystemRepository;
import com.gg.astronomy_ai_explorer.repository.StarRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StarServiceImpl implements StarService {

    private final StarRepository starRepository;
    private final SolarSystemRepository solarSystemRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;

    @Override
    public StarDto create(StarDto dto) {

        Star star = new Star();

        star.setName(dto.getName());
        star.setObjectSize(dto.getObjectSize());
        star.setObjectMass(dto.getObjectMass());
        star.setDescription(dto.getDescription());

        if (dto.getSolarSystemId() != null) {

            SolarSystem solarSystem = solarSystemRepository.findById(dto.getSolarSystemId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Solar system not found with id: "
                                            + dto.getSolarSystemId(), HttpStatus.NOT_FOUND));

            star.setSolarSystem(solarSystem);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            star.setDiscover(discover);
        }

        Star saved = starRepository.save(star);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.STAR, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.STAR,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public StarDto getById(Long id) {

        Star star = starRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Star not found with id: " + id, HttpStatus.NOT_FOUND));

        return toDto(star);
    }

    @Override
    public List<StarDto> getAll() {

        return starRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public StarDto update(Long id, StarDto dto) {

        Star star = starRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Star not found with id: " + id, HttpStatus.NOT_FOUND));

        star.setName(dto.getName());
        star.setObjectSize(dto.getObjectSize());
        star.setObjectMass(dto.getObjectMass());
        star.setDescription(dto.getDescription());

        if (dto.getSolarSystemId() != null) {

            SolarSystem solarSystem = solarSystemRepository.findById(dto.getSolarSystemId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Solar system not found with id: "
                                            + dto.getSolarSystemId(), HttpStatus.NOT_FOUND));

            star.setSolarSystem(solarSystem);

        } else {
            star.setSolarSystem(null);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            star.setDiscover(discover);

        }

        Star updated = starRepository.save(star);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.STAR, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.STAR,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        Star star = starRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Star not found with id: " + id, HttpStatus.NOT_FOUND));

        starRepository.delete(star);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.STAR,
                id
        );

        //
    }

    private StarDto toDto(Star star) {

        StarDto dto = new StarDto();

        dto.setId(star.getId());
        dto.setName(star.getName());
        dto.setObjectSize(star.getObjectSize());
        dto.setObjectMass(star.getObjectMass());
        dto.setDescription(star.getDescription());

        if (star.getSolarSystem() != null) {
            dto.setSolarSystemId(star.getSolarSystem().getId());
        }

        if (star.getDiscover() != null) {
            dto.setDiscoverId(star.getDiscover().getId());
        }

        return dto;
    }
}
