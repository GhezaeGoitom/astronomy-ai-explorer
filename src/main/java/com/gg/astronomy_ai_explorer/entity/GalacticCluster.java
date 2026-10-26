package com.gg.astronomy_ai_explorer.entity;

import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "galactic_cluster")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GalacticCluster {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "galactic_supercluster_id")
    private GalacticSuperCluster galacticSuperCluster;

    @Column(columnDefinition = "TEXT")
    private String description;
}