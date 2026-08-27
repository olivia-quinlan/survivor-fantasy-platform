package com.survivor.backend.repositories;

import com.survivor.backend.models.SeasonAppearance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonAppearanceRepository extends JpaRepository<SeasonAppearance,Long> {
    List<SeasonAppearance> findBySeasonIdOrderByFinalPlacementAsc(String seasonId);
}
