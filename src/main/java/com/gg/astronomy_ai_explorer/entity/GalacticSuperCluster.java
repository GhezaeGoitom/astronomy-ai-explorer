package com.gg.astronomy_ai_explorer.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "galactic_supercluster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalacticSuperCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}