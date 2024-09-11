package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.exceptions.ResourceNotFoundException;
import com.sirma.pairofplayers.helpers.Constants;
import com.sirma.pairofplayers.helpers.CsvReader;
import com.sirma.pairofplayers.models.Player;
import com.sirma.pairofplayers.models.Team;
import com.sirma.pairofplayers.models.dtos.PlayerDto;
import com.sirma.pairofplayers.repositories.PlayerRepository;
import com.sirma.pairofplayers.repositories.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class PlayerService extends AbstractService<Player> {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    private static final String filepath = Constants.PLAYER_FILE_PATH;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public void savePlayersFromCsvToDB() throws IOException {
        CsvReader csvReader = new CsvReader(filepath);
        List<String[]> rows = csvReader.readAll();
        csvReader.close();
        for (String[] row : rows) {
            Player player = new Player();
            List<Player> allPlayers = playerRepository.findAll();
            long id = Long.parseLong(row[0]);
            if (doesIdExist(allPlayers, id)) {
                continue;
            }
            long teamId = Long.parseLong(row[4]);
            player.setId(id);
            player.setTeamNumber(Integer.parseInt(row[1]));
            player.setPosition(row[2]);
            player.setFullName(row[3]);
            Team teamFromDb = teamRepository.findById(teamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", teamId));
            player.setTeam(teamFromDb);
            playerRepository.save(player);
        }
    }

    public Page<PlayerDto> getAll(Pageable pageable) {
        return this.playerRepository.findAllPlayers(pageable);
    }

    public Player getPlayer(Long playerId) {
        Player playerFromDb = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "playerId", playerId));
        return playerFromDb;
    }

    public PlayerDto add(PlayerDto playerDto) {
        Team team = teamRepository.findById(playerDto.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", playerDto.getTeamId()));

        Player player = new Player();
        player.setTeamNumber(playerDto.getTeamNumber());
        player.setPosition(playerDto.getPosition());
        player.setFullName(playerDto.getFullName());
        player.setTeam(team);

        Player savedPlayer = playerRepository.save(player);

        return mapToPlayerDto(savedPlayer);
    }

    public PlayerDto updatePlayer(Long playerId, PlayerDto playerDto) {
        Player playerFromDb = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "playerId", playerId));

        Team team = teamRepository.findById(playerDto.getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", playerDto.getTeamId()));

        playerFromDb.setTeamNumber(playerDto.getTeamNumber());
        playerFromDb.setPosition(playerDto.getPosition());
        playerFromDb.setFullName(playerDto.getFullName());
        playerFromDb.setTeam(team);
        Player savedPlayer = playerRepository.save(playerFromDb);
        return mapToPlayerDto(savedPlayer);
    }
    public PlayerDto deletePlayer(Long playerId) {
        Player playerFromDb = playerRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player", "playerId", playerId));

        playerRepository.deleteById(playerId);
        return mapToPlayerDto(playerFromDb);
    }

    private PlayerDto mapToPlayerDto(Player player) {
        return new PlayerDto(
                player.getTeamNumber(),
                player.getPosition(),
                player.getFullName(),
                player.getTeam().getId()
        );
    }

}
