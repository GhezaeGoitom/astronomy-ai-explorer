package com.gg.astronomy_ai_explorer.repository;

import com.gg.astronomy_ai_explorer.entity.Supernova;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupernovaRepository extends JpaRepository<Supernova, Long> {
}