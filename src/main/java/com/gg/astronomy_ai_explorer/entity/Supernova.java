package com.gg.astronomy_ai_explorer.entity;

import com.gg.astronomy_ai_explorer.entity.user.Discover;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "supernova")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supernova {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    private Long brightness;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "universe_id")
    private Universe universe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discover_id")
    private Discover discover;

    @Column(columnDefinition = "TEXT")
    private String description;
}