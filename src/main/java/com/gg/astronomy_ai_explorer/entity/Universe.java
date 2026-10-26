package com.gg.astronomy_ai_explorer.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "universe")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Universe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 50)
    private String coordinate;
}