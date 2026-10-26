package com.gg.astronomy_ai_explorer.entity;

import com.gg.astronomy_ai_explorer.entity.user.Discover;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "star")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Star {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "object_size")
    private Long objectSize;

    @Column(name = "object_mass")
    private Long objectMass;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solar_system_id")
    private SolarSystem solarSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_id")
    private Discover discover;
}
