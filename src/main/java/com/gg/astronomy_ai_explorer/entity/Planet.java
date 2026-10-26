package com.gg.astronomy_ai_explorer.entity;


import com.gg.astronomy_ai_explorer.entity.user.Discover;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import jakarta.persistence.*;

@Entity
@Table(name = "planet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @Column(name = "object_size")
    private Long objectSize;

    @Column(name = "object_mass")
    private Long objectMass;

    private Integer score;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solar_system_id")
    private SolarSystem solarSystem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_id")
    private Discover discover;
}