package com.gg.astronomy_ai_explorer.repository;

import com.gg.astronomy_ai_explorer.entity.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
}
