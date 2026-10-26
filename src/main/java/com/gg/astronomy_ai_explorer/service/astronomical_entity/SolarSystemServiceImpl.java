package com.gg.astronomy_ai_explorer.service.astronomical_entity;


import com.gg.astronomy_ai_explorer.component.embedding.DiscoveryContentBuilder;
import com.gg.astronomy_ai_explorer.dto.SolarSystemDto;
import com.gg.astronomy_ai_explorer.entity.Galaxy;
import com.gg.astronomy_ai_explorer.entity.SolarSystem;
import com.gg.astronomy_ai_explorer.entity.user.Discover;
import com.gg.astronomy_ai_explorer.exception.ApiException;
import com.gg.astronomy_ai_explorer.repository.GalaxyRepository;
import com.gg.astronomy_ai_explorer.repository.SolarSystemRepository;
import com.gg.astronomy_ai_explorer.repository.user.DiscoverRepository;
import com.gg.astronomy_ai_explorer.service.embedding.DiscoveryEmbeddingService;
import com.gg.astronomy_ai_explorer.util.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SolarSystemServiceImpl implements SolarSystemService {

    private final SolarSystemRepository solarSystemRepository;
    private final GalaxyRepository galaxyRepository;
    private final DiscoverRepository discoverRepository;
    private final DiscoveryContentBuilder discoveryContentBuilder;
    private final DiscoveryEmbeddingService discoveryEmbeddingService;


    @Override
    public List<SolarSystemDto> getAllSolarSystems() {
        return solarSystemRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .toList();
    }

    @Override
    public SolarSystemDto getSolarSystemById(Long id) {
        SolarSystem solarSystem = solarSystemRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException("Solar system not found with id: " + id, HttpStatus.NOT_FOUND));

        return convertToDto(solarSystem);
    }

    @Override
    public SolarSystemDto createSolarSystem(SolarSystemDto solarSystemDto) {

        Galaxy galaxy = null;
        Discover discover = null;

        if (solarSystemDto.getGalaxyId() != null) {
            galaxy = galaxyRepository.findById(solarSystemDto.getGalaxyId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galaxy not found with id: "
                                            + solarSystemDto.getGalaxyId(), HttpStatus.NOT_FOUND));
        }

        if (solarSystemDto.getDiscoverId() != null) {

            discover = discoverRepository.findById(solarSystemDto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + solarSystemDto.getDiscoverId(), HttpStatus.NOT_FOUND));

         }

        SolarSystem solarSystem = new SolarSystem();

        solarSystem.setName(solarSystemDto.getName());
        solarSystem.setDescription(solarSystemDto.getDescription());
        solarSystem.setGalaxy(galaxy);
        solarSystem.setDiscover(discover);

        SolarSystem saved =
                solarSystemRepository.save(solarSystem);

        // create embedding
        String content = discoveryContentBuilder.buildContent(EntityType.SOLAR_SYSTEM, saved);
        discoveryEmbeddingService.createEmbedding(
                EntityType.SOLAR_SYSTEM,
                saved.getId(),
                content
        );
        //

        return convertToDto(saved);
    }

    @Override
    public SolarSystemDto updateSolarSystem(
            Long id,
            SolarSystemDto solarSystemDto) {

        SolarSystem solarSystem = solarSystemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Solar system not found with id: " + id));

        if (solarSystemDto.getGalaxyId() != null) {
            Galaxy galaxy = galaxyRepository.findById(
                            solarSystemDto.getGalaxyId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Galaxy not found with id: "
                                            + solarSystemDto.getGalaxyId(), HttpStatus.NOT_FOUND));

            solarSystem.setGalaxy(galaxy);
        } else {
            solarSystem.setGalaxy(null);
        }

        if (solarSystemDto.getDiscoverId() != null) {

            Discover discover = discoverRepository.findById(solarSystemDto.getDiscoverId())
                    .orElseThrow(() ->
                            new ApiException(
                                    "Discover not found with id: "
                                            + solarSystemDto.getDiscoverId(), HttpStatus.NOT_FOUND));

            solarSystem.setDiscover(discover);

        }

        solarSystem.setName(solarSystemDto.getName());
        solarSystem.setDescription(solarSystemDto.getDescription());

        SolarSystem updated =
                solarSystemRepository.save(solarSystem);

        // update embedding
        String content = discoveryContentBuilder.buildContent(EntityType.SOLAR_SYSTEM, updated);

        discoveryEmbeddingService.updateEmbedding(
                EntityType.SOLAR_SYSTEM,
                updated.getId(),
                content
        );
        //

        return convertToDto(updated);
    }

    @Override
    public void deleteSolarSystem(Long id) {

        if (!solarSystemRepository.existsById(id)) {
            throw new ApiException(
                    "Solar system not found with id: " + id, HttpStatus.NOT_FOUND);
        }
        solarSystemRepository.deleteById(id);
        // delete embedding
        discoveryEmbeddingService.deleteEmbedding(
                EntityType.SOLAR_SYSTEM,
                id
        );

        //
    }

    private SolarSystemDto convertToDto(SolarSystem solarSystem) {

        SolarSystemDto dto = new SolarSystemDto();

        dto.setId(solarSystem.getId());
        dto.setName(solarSystem.getName());
        dto.setDescription(solarSystem.getDescription());
        dto.setDiscoverId(solarSystem.getDiscover().getId());

        if (solarSystem.getGalaxy() != null) {
            dto.setGalaxyId(solarSystem.getGalaxy().getId());
        }


        return dto;
    }
}
