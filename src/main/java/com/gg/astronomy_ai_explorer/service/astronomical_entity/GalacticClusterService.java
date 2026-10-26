package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.GalacticClusterDto;

import java.util.List;

public interface GalacticClusterService {

    GalacticClusterDto create(GalacticClusterDto dto);

    GalacticClusterDto getById(Long id);

    List<GalacticClusterDto> getAll();

    GalacticClusterDto update(Long id, GalacticClusterDto dto);

    void delete(Long id);
}
