package com.gg.astronomy_ai_explorer.repository;

import com.gg.astronomy_ai_explorer.entity.Star;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StarRepository extends JpaRepository<Star, Long> {
}