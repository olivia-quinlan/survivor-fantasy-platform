package com.survivor.backend.repositories;

import com.survivor.backend.models.Tribe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TribeRepository extends JpaRepository<Tribe,Long> {
    List<Tribe> findBySeasonId(String seasonId);
}
