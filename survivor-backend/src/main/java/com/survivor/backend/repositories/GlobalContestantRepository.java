package com.survivor.backend.repositories;

import com.survivor.backend.models.GlobalContestant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GlobalContestantRepository extends JpaRepository<GlobalContestant, Long> {
}
