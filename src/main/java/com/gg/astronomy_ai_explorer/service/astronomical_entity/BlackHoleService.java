package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.BlackHoleDto;

import java.util.List;

public interface BlackHoleService {

    BlackHoleDto create(BlackHoleDto dto);

    BlackHoleDto getById(Long id);

    List<BlackHoleDto> getAll();

    BlackHoleDto update(Long id, BlackHoleDto dto);

    void delete(Long id);
}
