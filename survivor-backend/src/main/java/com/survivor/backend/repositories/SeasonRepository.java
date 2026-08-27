package com.survivor.backend.repositories;

import com.survivor.backend.models.Season;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<Season,String> {

}
