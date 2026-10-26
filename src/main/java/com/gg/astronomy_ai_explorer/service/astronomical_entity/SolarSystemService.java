package com.gg.astronomy_ai_explorer.service.astronomical_entity;

import com.gg.astronomy_ai_explorer.dto.SolarSystemDto;

import java.util.List;

public interface SolarSystemService {

    List<SolarSystemDto> getAllSolarSystems();

    SolarSystemDto getSolarSystemById(Long id);

    SolarSystemDto createSolarSystem(SolarSystemDto solarSystemDto);

    SolarSystemDto updateSolarSystem(Long id, SolarSystemDto solarSystemDto);

    void deleteSolarSystem(Long id);
}
