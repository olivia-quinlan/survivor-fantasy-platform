package com.survivor.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GlobalContestantHistoryDto {
    private Long appearanceId;
    private String seasonId;
    private String seasonName;
    private String placementSummary;
}
