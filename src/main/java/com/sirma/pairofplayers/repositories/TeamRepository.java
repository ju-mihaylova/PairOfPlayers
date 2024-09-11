package com.sirma.pairofplayers.repositories;

import com.sirma.pairofplayers.models.Team;
import com.sirma.pairofplayers.models.dtos.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT new com.sirma.pairofplayers.models.dtos.TeamDto(t.name, t.managerFullName, t.groupChar) FROM Team t")
    Page<TeamDto> findAllTeams(Pageable pageable);
}
