package com.sirma.pairofplayers.repositories;

import com.sirma.pairofplayers.models.Match;
import com.sirma.pairofplayers.models.dtos.MatchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    @Query("SELECT new com.sirma.pairofplayers.models.dtos.MatchDto(m.aTeam.id, m.bTeam.id, m.date, m.score) FROM Match m")
    Page<MatchDto> findAllMatches(Pageable pageable);
    @Query("SELECT m FROM Match m WHERE m.score LIKE CONCAT('%', :character, '%')")
    List<Match> findMatchesByScoreContainingCharacter(String character);
}
