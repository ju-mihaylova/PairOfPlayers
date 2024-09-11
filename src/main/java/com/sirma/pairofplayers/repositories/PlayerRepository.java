package com.sirma.pairofplayers.repositories;

import com.sirma.pairofplayers.models.Player;
import com.sirma.pairofplayers.models.dtos.PlayerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    @Query("SELECT new com.sirma.pairofplayers.models.dtos.PlayerDto(p.teamNumber, p.position, p.fullName, p.team.id) FROM Player p")
    Page<PlayerDto> findAllPlayers(Pageable pageable);
}
