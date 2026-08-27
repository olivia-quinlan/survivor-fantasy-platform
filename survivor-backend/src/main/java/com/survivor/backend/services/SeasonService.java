package com.survivor.backend.services;

import com.survivor.backend.dtos.SeasonContestantDto;
import com.survivor.backend.dtos.TribeHistoryDto;
import com.survivor.backend.models.ContestantTribeHistory;
import com.survivor.backend.models.SeasonAppearance;
import com.survivor.backend.repositories.ContestantTribeHistoryRepository;
import com.survivor.backend.repositories.SeasonAppearanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonAppearanceRepository appearanceRepository;
    private final ContestantTribeHistoryRepository historyRepository;

    public List<SeasonContestantDto> getSeasonRoster(String seasonId){
        List <SeasonAppearance> appearances = appearanceRepository.findBySeasonIdOrderByFinalPlacementAsc(seasonId);
        List<SeasonContestantDto> seasonContestantDtoList = new ArrayList<>();
        int totalContestants = appearances.size();

        for (SeasonAppearance appearance : appearances) {
            SeasonContestantDto dto = new SeasonContestantDto();
            dto.setAppearanceId(appearance.getId());
            dto.setName(appearance.getContestant().getName());
            dto.setHometown(appearance.getContestant().getHometown());
            dto.setWikiUrl(appearance.getContestant().getWikiUrl());
            dto.setStatus(appearance.getStatus());
            dto.setFinalPlacement(appearance.getFinalPlacement());
            dto.setPlacementSummary(toOrdinal(appearance.getFinalPlacement()));
            dto.setImageUrl(appearance.getImageUrl());
            dto.setOccupation(appearance.getOccupation());





            List<ContestantTribeHistory> historyList =
                    historyRepository.findByAppearanceIdOrderByPhaseOrderAsc((appearance.getId()));

            List<TribeHistoryDto> timeline = new ArrayList<>();

            for (ContestantTribeHistory history : historyList) {
                TribeHistoryDto tribeHistoryDto = new TribeHistoryDto();
                tribeHistoryDto.setPhaseOrder(history.getPhaseOrder());
                tribeHistoryDto.setTribeName(history.getTribe().getName());
                tribeHistoryDto.setTribeColorHex(history.getTribe().getColorHex());
                tribeHistoryDto.setTribeStatus(history.getTribeStatus());
                tribeHistoryDto.setEpisodeNumber(history.getEpisodeNumber());
                tribeHistoryDto.setDayNumber(history.getDayNumber());
                timeline.add(tribeHistoryDto);
            }

            dto.setTimeline(timeline);

            ContestantTribeHistory currentTribe = historyList.get(historyList.size()-1);
            dto.setCurrentTribeName(currentTribe.getTribe().getName());
            dto.setCurrentTribeColorHex(currentTribe.getTribe().getColorHex());

            seasonContestantDtoList.add(dto);
        }
        return seasonContestantDtoList;
    }

    public SeasonContestantDto getSeasonContestant(Long appearanceId){
        SeasonAppearance appearance = appearanceRepository.findById(appearanceId)
                .orElseThrow(() -> new RuntimeException("Contestant appearance not found with ID: " + appearanceId));
        SeasonContestantDto dto = new SeasonContestantDto();

        dto.setAppearanceId(appearance.getId());
        dto.setName(appearance.getContestant().getName());
        dto.setHometown(appearance.getContestant().getHometown());
        dto.setOccupation(appearance.getOccupation());
        dto.setWikiUrl(appearance.getContestant().getWikiUrl());
        dto.setImageUrl(appearance.getImageUrl());
        dto.setFinalPlacement(appearance.getFinalPlacement());
        dto.setPlacementSummary(toOrdinal(appearance.getFinalPlacement()));
        dto.setStatus(appearance.getStatus());

        List<ContestantTribeHistory> history = historyRepository.findByAppearanceIdOrderByPhaseOrderAsc((appearance.getId()));
        List<TribeHistoryDto> timeline = new ArrayList<>();

        for (ContestantTribeHistory history1 : history) {
            TribeHistoryDto tribeHistoryDto = new TribeHistoryDto();
            tribeHistoryDto.setPhaseOrder(history1.getPhaseOrder());
            tribeHistoryDto.setTribeName(history1.getTribe().getName());
            tribeHistoryDto.setTribeColorHex(history1.getTribe().getColorHex());
            tribeHistoryDto.setTribeStatus(history1.getTribeStatus());
            tribeHistoryDto.setEpisodeNumber(history1.getEpisodeNumber());
            tribeHistoryDto.setDayNumber(history1.getDayNumber());
            timeline.add(tribeHistoryDto);
        }
        dto.setTimeline(timeline);

        if (!history.isEmpty()) {
            ContestantTribeHistory currentTribe = history.get(history.size() - 1);
            dto.setCurrentTribeName(currentTribe.getTribe().getName());
            dto.setCurrentTribeColorHex(currentTribe.getTribe().getColorHex());
        }

        return dto;

    }

    private String toOrdinal(int finalPlacement) {
        if (finalPlacement % 100 == 11|finalPlacement % 100 == 12|finalPlacement % 100 == 13) {
            return finalPlacement + "th";
        } else {
            switch (finalPlacement % 10) {
                case 1:
                    return finalPlacement + "st";
                case 2:
                    return finalPlacement + "nd";
                case 3:
                    return finalPlacement + "rd";
                case 4, 5, 6, 7, 8, 9, 0:
                    return finalPlacement + "th";
                default:
                    return finalPlacement + "st";
            }
        }
    }

    public String getServiceMessage(){
        return "SeasonService is online!";
    }
}
