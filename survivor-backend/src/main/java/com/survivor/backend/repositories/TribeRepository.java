package com.survivor.backend.repositories;

import com.survivor.backend.models.Tribe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TribeRepository extends JpaRepository<Tribe,Long> {
}
