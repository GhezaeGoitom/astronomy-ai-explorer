package com.gg.astronomy_ai_explorer.repository;

import com.gg.astronomy_ai_explorer.entity.BlackHole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlackHoleRepository extends JpaRepository<BlackHole, Long> {
}
