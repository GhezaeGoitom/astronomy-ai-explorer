package com.gg.astronomy_ai_explorer.entity;

import com.gg.astronomy_ai_explorer.entity.user.Discover;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "solar_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SolarSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "galaxy_id")
    private Galaxy galaxy;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_id")
    private Discover discover;

}
