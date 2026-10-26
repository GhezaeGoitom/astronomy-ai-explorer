package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.UniverseDto;

import java.util.List;

public interface UniverseService {

    List<UniverseDto> getAll();

    UniverseDto getById(Long id);

    UniverseDto create(UniverseDto dto);

    UniverseDto update(Long id, UniverseDto dto);

    void delete(Long id);
}
