package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.SupernovaDto;

import java.util.List;

public interface SupernovaService {

    SupernovaDto create(SupernovaDto dto);

    SupernovaDto getById(Long id);

    List<SupernovaDto> getAll();

    SupernovaDto update(Long id, SupernovaDto dto);

    void delete(Long id);
}