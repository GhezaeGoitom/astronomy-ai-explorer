package com.gg.astronomy_ai_explorer.repository.user;

import com.gg.astronomy_ai_explorer.entity.user.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
