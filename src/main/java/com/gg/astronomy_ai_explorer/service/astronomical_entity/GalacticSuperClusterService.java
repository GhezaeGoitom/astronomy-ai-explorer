package com.gg.astronomy_ai_explorer.service.astronomical_entity;


import com.gg.astronomy_ai_explorer.dto.GalacticSuperClusterDto;

import java.util.List;

public interface GalacticSuperClusterService {

    List<GalacticSuperClusterDto> getAll();

    GalacticSuperClusterDto getById(Long id);

    GalacticSuperClusterDto create(GalacticSuperClusterDto dto);

    GalacticSuperClusterDto update(
            Long id,
            GalacticSuperClusterDto dto);

    void delete(Long id);
}
