package com.gg.astronomy_ai_explorer.service.astronomical_entity;


import com.gg.astronomy_ai_explorer.dto.PlanetDto;

import java.util.List;

public interface PlanetService {

    PlanetDto create(PlanetDto dto);

    PlanetDto getById(Long id);

    List<PlanetDto> getAll();

    PlanetDto update(Long id, PlanetDto dto);

    void delete(Long id);
}