package com.survivor.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TribeHistoryDto {
    private int phaseOrder;
    private String tribeName;
    private String tribeColorHex;
    private String tribeStatus;
    private int episodeNumber;
    private int dayNumber;
}
