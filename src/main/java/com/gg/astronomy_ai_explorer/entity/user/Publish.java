package com.gg.astronomy_ai_explorer.entity.user;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "publish")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Publish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_published")
    private LocalDate datePublished;

    @Column(length = 100)
    private String place;
}
