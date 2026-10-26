package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.StarDto;

import java.util.List;

public interface StarService {

    StarDto create(StarDto dto);

    StarDto getById(Long id);

    List<StarDto> getAll();

    StarDto update(Long id, StarDto dto);

    void delete(Long id);
}