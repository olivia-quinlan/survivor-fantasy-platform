package com.survivor.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contestant_tribe_history")
public class ContestantTribeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "appearance_id", referencedColumnName = "id")
    private SeasonAppearance appearance;

    @ManyToOne
    @JoinColumn(name = "tribe_id", referencedColumnName = "id")
    private Tribe tribe;

    private int phaseOrder;

    @Column(length = 50)
    private String tribeStatus;

    private int episodeNumber;

    private int dayNumber;
}
