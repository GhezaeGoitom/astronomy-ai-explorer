package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.PlanetDto;
import com.gg.astronomy_ai_explorer.entity.Planet;
import com.gg.astronomy_ai_explorer.entity.SolarSystem;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.PlanetRepository;
import com.gg.astronomy_ai_explorer.repository.SolarSystemRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingServiceImpl;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanetServiceImpl implements PlanetService {

    private final PlanetRepository planetRepository;
    private final SolarSystemRepository solarSystemRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryEmbeddingServiceImpl discoveryEmbeddingService;
    private final DiscoveryContentBuilder discoveryContentBuilder;


    @Override
    public PlanetDto create(PlanetDto dto) {

        Planet planet = new Planet();

        planet.setName(dto.getName());
        planet.setObjectSize(dto.getObjectSize());
        planet.setObjectMass(dto.getObjectMass());
        planet.setScore(dto.getScore());
        planet.setDescription(dto.getDescription());

        if (dto.getSolarSystemId() != null) {

            SolarSystem solarSystem = solarSystemRepository.findById(dto.getSolarSystemId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Solar system not found with id: "
                                            + dto.getSolarSystemId(), HttpStatus.NOT_FOUND));

            planet.setSolarSystem(solarSystem);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            planet.setDiscover(discover);
        }



        Planet saved = planetRepository.save(planet);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.PLANET, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.PLANET,
                saved.getId(),
                content
        );
        //

        return toDto(saved);
    }

    @Override
    public PlanetDto getById(Long id) {

        Planet planet = planetRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Planet not found with id: " + id, HttpStatus.NOT_FOUND));



        return toDto(planet);
    }

    @Override
    public List<PlanetDto> getAll() {

        return planetRepository.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public PlanetDto update(Long id, PlanetDto dto) {

        Planet planet = planetRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Planet not found with id: " + id, HttpStatus.NOT_FOUND));

        planet.setName(dto.getName());
        planet.setObjectSize(dto.getObjectSize());
        planet.setObjectMass(dto.getObjectMass());
        planet.setScore(dto.getScore());
        planet.setDescription(dto.getDescription());

        if (dto.getSolarSystemId() != null) {

            SolarSystem solarSystem = solarSystemRepository.findById(dto.getSolarSystemId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Solar system not found with id: "
                                            + dto.getSolarSystemId(), HttpStatus.NOT_FOUND));

            planet.setSolarSystem(solarSystem);

        } else {
            planet.setSolarSystem(null);
        }

        if (dto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(dto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + dto.getDiscoverId(), HttpStatus.NOT_FOUND));

            planet.setDiscover(discover);

        } else {
            planet.setDiscover(null);
        }

        Planet updated = planetRepository.save(planet);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.PLANET, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.PLANET,
                updated.getId(),
                content
        );
        //

        return toDto(updated);
    }

    @Override
    public void delete(Long id) {

        Planet planet = planetRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(
                                "Planet not found with id: " + id, HttpStatus.NOT_FOUND));

        planetRepository.delete(planet);

        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.PLANET,
                id
        );

        //
    }

    private PlanetDto toDto(Planet planet) {

        PlanetDto dto = new PlanetDto();

        dto.setId(planet.getId());
        dto.setName(planet.getName());
        dto.setObjectSize(planet.getObjectSize());
        dto.setObjectMass(planet.getObjectMass());
        dto.setScore(planet.getScore());
        dto.setDescription(planet.getDescription());

        if (planet.getSolarSystem() != null) {
            dto.setSolarSystemId(planet.getSolarSystem().getId());
        }

        if (planet.getDiscover() != null) {
            dto.setDiscoverId(planet.getDiscover().getId());
        }

        return dto;
    }
}