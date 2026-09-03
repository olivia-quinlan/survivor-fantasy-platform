package com.survivor.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonDto {

    private String seasonId;

    private String seasonName;

    private LocalDateTime premiereDate;

    private LocalDateTime finaleDate;

    private String location;

    private Integer numberOfDays;

    private Integer castSize;

    private List<String> tribeColors;
}
