package com.survivor.backend.services;

import com.survivor.backend.dtos.GlobalContestantHistoryDto;
import com.survivor.backend.dtos.SeasonContestantDto;
import com.survivor.backend.dtos.SeasonDto;
import com.survivor.backend.dtos.TribeHistoryDto;
import com.survivor.backend.models.*;
import com.survivor.backend.repositories.ContestantTribeHistoryRepository;
import com.survivor.backend.repositories.SeasonAppearanceRepository;
import com.survivor.backend.repositories.SeasonRepository;
import com.survivor.backend.repositories.TribeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SeasonService {
    private final SeasonAppearanceRepository appearanceRepository;
    private final ContestantTribeHistoryRepository historyRepository;
    private final SeasonRepository seasonRepository;
    private final TribeRepository tribeRepository;

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
            dto.setSeasonId(appearance.getSeason().getId());
            dto.setContestantId(appearance.getContestant().getId());





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

            if (!historyList.isEmpty()) {
                ContestantTribeHistory currentTribe = historyList.get(historyList.size()-1);
                dto.setCurrentTribeName(currentTribe.getTribe().getName());
                dto.setCurrentTribeColorHex(currentTribe.getTribe().getColorHex());
            }

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
        dto.setSeasonId(appearance.getSeason().getId());
        dto.setContestantId(appearance.getContestant().getId());
        dto.setBio(appearance.getBio());
        dto.setEliminationDay(appearance.getEliminationDay());

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

    public List<SeasonDto> getAllSeasons(){
        List<Season> seasons = seasonRepository.findAll();
        List<SeasonDto> seasonDtoList = new ArrayList<>();
        for (Season season : seasons) {
            SeasonDto seasonDto = new SeasonDto();
            seasonDto.setSeasonId(season.getId());
            seasonDto.setSeasonName(season.getName());
            seasonDto.setPremiereDate(season.getPremiereDate());
            seasonDto.setFinaleDate(season.getFinaleDate());
            seasonDto.setLocation(season.getLocation());
            seasonDto.setNumberOfDays(season.getNumberOfDays());
            seasonDto.setCastSize(season.getCastSize());

            List<Tribe> tribes = tribeRepository.findBySeasonId(season.getId());
            List<Tribe> nonMergeTribes = new ArrayList<>();
            List<Tribe> mergeTribes = new ArrayList<>();

            for (Tribe tribe : tribes) {
                if (historyRepository.existsByTribeIdAndTribeStatus(tribe.getId(), "MERGE")) {
                    mergeTribes.add(tribe);
                } else  {
                    nonMergeTribes.add(tribe);
                }
            }

            List<Tribe> orderedTribes = new ArrayList<>();
            orderedTribes.addAll(nonMergeTribes);
            orderedTribes.addAll(mergeTribes);


            List<String> tribeColors = new ArrayList<>();
            for (Tribe tribe : orderedTribes) {
                tribeColors.add(tribe.getColorHex());
            }
            seasonDto.setTribeColors(tribeColors);

            seasonDtoList.add(seasonDto);
        }
        return seasonDtoList;
    }

    public List<GlobalContestantHistoryDto> getGlobalContestantHistory(Long contestantId){
        List<SeasonAppearance> appearances = appearanceRepository.findByContestantIdOrderBySeasonPremiereDate(contestantId);
        List<GlobalContestantHistoryDto> globalContestantHistoryDtoList = new ArrayList<>();
        for (SeasonAppearance appearance : appearances) {
            GlobalContestantHistoryDto contestantHistory = new GlobalContestantHistoryDto();
            contestantHistory.setAppearanceId(appearance.getId());
            contestantHistory.setSeasonId(appearance.getSeason().getId());
            contestantHistory.setSeasonName(appearance.getSeason().getName());
            contestantHistory.setPlacementSummary(toOrdinal(appearance.getFinalPlacement()));

            globalContestantHistoryDtoList.add(contestantHistory);
        }
        return globalContestantHistoryDtoList;
    }

    public SeasonDto getSeasonById(String seasonId){
        SeasonDto seasonDto = new SeasonDto();
        Season season = seasonRepository.findById(seasonId)
                .orElseThrow(() -> new RuntimeException("Season not found with ID: " + seasonId));
        seasonDto.setSeasonId(season.getId());
        seasonDto.setSeasonName(season.getName());
        seasonDto.setPremiereDate(season.getPremiereDate());
        seasonDto.setFinaleDate(season.getFinaleDate());
        seasonDto.setCastSize(season.getCastSize());
        seasonDto.setLocation(season.getLocation());
        seasonDto.setNumberOfDays(season.getNumberOfDays());

        List<Tribe> tribes = tribeRepository.findBySeasonId(season.getId());
        List<Tribe> nonMergeTribes = new ArrayList<>();
        List<Tribe> mergeTribes = new ArrayList<>();

        for (Tribe tribe : tribes) {
            if (historyRepository.existsByTribeIdAndTribeStatus(tribe.getId(), "MERGE")) {
                mergeTribes.add(tribe);
            } else  {
                nonMergeTribes.add(tribe);
            }
        }

        List<Tribe> orderedTribes = new ArrayList<>();
        orderedTribes.addAll(nonMergeTribes);
        orderedTribes.addAll(mergeTribes);


        List<String> tribeColors = new ArrayList<>();
        for (Tribe tribe : orderedTribes) {
            tribeColors.add(tribe.getColorHex());
        }
        seasonDto.setTribeColors(tribeColors);

        return seasonDto;
    }

    private String toOrdinal(Integer finalPlacement) {
        if (finalPlacement == null) {
            return null;
        }
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
