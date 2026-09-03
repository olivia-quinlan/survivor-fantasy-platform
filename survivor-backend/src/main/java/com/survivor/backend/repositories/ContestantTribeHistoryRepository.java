package com.survivor.backend.repositories;

import com.survivor.backend.models.ContestantTribeHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestantTribeHistoryRepository extends JpaRepository<ContestantTribeHistory,Long> {
    List<ContestantTribeHistory> findByAppearanceIdOrderByPhaseOrderAsc(Long appearanceId);
    boolean existsByTribeIdAndTribeStatus(Long tribeId,String tribeStatus);
}
