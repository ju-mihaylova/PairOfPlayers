package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.exceptions.ResourceNotFoundException;
import com.sirma.pairofplayers.helpers.Constants;
import com.sirma.pairofplayers.helpers.CsvReader;
import com.sirma.pairofplayers.models.Team;
import com.sirma.pairofplayers.models.dtos.TeamDto;
import com.sirma.pairofplayers.repositories.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class TeamService extends AbstractService<Team> {
    private static final String filepath = Constants.TEAM_FILE_PATH;
    private final TeamRepository teamRepository;
    private final ModelMapper modelMapper;

    public TeamService(TeamRepository teamRepository, ModelMapper modelMapper) {
        this.teamRepository = teamRepository;
        this.modelMapper = modelMapper;
    }

    public void saveTeamsFromCsvToDB() throws IOException {
        CsvReader csvReader = new CsvReader(filepath);
        List<String[]> rows = csvReader.readAll();
        csvReader.close();
        for (String[] row : rows) {
            Team team = new Team();
            List<Team> allTeams = teamRepository.findAll();
            long id = Long.parseLong(row[0]);
            if (doesIdExist(allTeams, id)) {
                continue;
            }
            team.setId(id);
            team.setName(row[1]);
            team.setManagerFullName(row[2]);
            team.setGroupChar(row[3].charAt(0));
            teamRepository.save(team);
        }
    }

    public Page<TeamDto> getAll(Pageable pageable) {
        return this.teamRepository.findAllTeams(pageable);
    }

    public Team getTeam(Long teamId) {
        Team teamFromDb = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", teamId));
        return teamFromDb;
    }

    public TeamDto add(TeamDto teamDto) {
        Team team = modelMapper.map(teamDto, Team.class);
        Team savedTeam = teamRepository.save(team);
        return modelMapper.map(savedTeam, TeamDto.class);
    }

    public TeamDto updateTeam(Long teamId, TeamDto teamDto) {
        Team teamFromDb = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", teamId));

        teamFromDb.setName(teamDto.getName());
        teamFromDb.setManagerFullName(teamDto.getManagerFullName());
        teamFromDb.setGroupChar(teamDto.getGroupChar());
        return modelMapper.map(teamRepository.save(teamFromDb), TeamDto.class);
    }

    public TeamDto deleteTeam(Long teamId) {
        Team teamFromDb = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", teamId));

        teamRepository.deleteById(teamId);
        return modelMapper.map(teamFromDb, TeamDto.class);
    }

}
