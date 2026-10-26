package com.gg.astronomy_ai_explorer.repository.user;

import com.gg.astronomy_ai_explorer.entity.user.AppRole;
import com.gg.astronomy_ai_explorer.entity.user.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppRoleRepository extends JpaRepository<AppRole, Integer> {

    Optional<AppRole> findByName(String name);
}
