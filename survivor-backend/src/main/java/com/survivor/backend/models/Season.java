package com.survivor.backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "seasons")

public class Season {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime premiereDate;

    private Integer premiereRosterSize;

    private Integer mergeRosterSize;

    private Integer sitdEpisodeOffset;

    private String location;

    private Integer numberOfDays;

    private Integer castSize;

    private LocalDateTime finaleDate;
}
