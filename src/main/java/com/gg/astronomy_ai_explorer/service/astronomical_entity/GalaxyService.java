package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.GalaxyDto;

import java.util.List;

public interface GalaxyService {

    GalaxyDto create(GalaxyDto dto);

    List<GalaxyDto> getAll();

    GalaxyDto getById(Long id);

    GalaxyDto update(Long id, GalaxyDto dto);

    void delete(Long id);
}
