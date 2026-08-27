package com.survivor.backend.controllers;

import com.survivor.backend.dtos.SeasonContestantDto;
import com.survivor.backend.services.SeasonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/public")
@RequiredArgsConstructor
public class SeasonController {
    private final SeasonService seasonService;

    @GetMapping("/test")
    public String testEndpoint(){
        return "Hello World";
    }

    @GetMapping("/seasons/{seasonId}/contestants")
    public List<SeasonContestantDto> getSeasonRoster(@PathVariable String seasonId) {
        return seasonService.getSeasonRoster(seasonId);
    }

    @GetMapping("/contestants/{appearanceId}")
    public SeasonContestantDto getSeasonContestant(@PathVariable Long appearanceId) {
        return seasonService.getSeasonContestant(appearanceId);
    }
}
