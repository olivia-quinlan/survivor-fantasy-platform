package com.survivor.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeasonContestantDto {
    private Long appearanceId;
    private String name;
    private String hometown;
    private String wikiUrl;
    private Integer finalPlacement;
    private String status;
    private String currentTribeName;
    private String currentTribeColorHex;
    private List<TribeHistoryDto> timeline;
    private String imageUrl;
    private String occupation;
    private String placementSummary;
    private String seasonId;
    private Long contestantId;
    private String bio;
    private Integer eliminationDay;
}
