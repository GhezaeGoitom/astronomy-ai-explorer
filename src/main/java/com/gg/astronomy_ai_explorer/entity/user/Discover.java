package com.gg.astronomy_ai_explorer.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "discover")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discover {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_discovered")
    private LocalDate dateDiscovered;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 100)
    private String coordinate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publish_id")
    private Publish publish;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discovered_by")
    private Person discoveredBy;
}
