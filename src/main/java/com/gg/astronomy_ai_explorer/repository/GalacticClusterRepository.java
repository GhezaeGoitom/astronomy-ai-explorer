package com.gg.astronomy_ai_explorer.repository;

import com.gg.astronomy_ai_explorer.entity.GalacticCluster;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalacticClusterRepository
        extends JpaRepository<GalacticCluster, Long> {
}