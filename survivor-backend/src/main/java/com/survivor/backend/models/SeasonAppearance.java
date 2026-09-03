package com.survivor.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "season_appearances")

public class SeasonAppearance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "contestant_id", referencedColumnName = "id")
    private GlobalContestant contestant;

    @ManyToOne
    @JoinColumn(name = "season_id", referencedColumnName = "id")
    private Season season;

    private Integer finalPlacement;

    @Column(length = 50)
    private String status;

    @Column(name = "image_url", length = 500)
    private String imageUrl;

    @Column(name = "occupation", length = 150)
    private String occupation;

    @Column(columnDefinition = "TEXT")
    private String bio;

    @Column(name = "elimination_day")
    private Integer eliminationDay;

}
