package com.sirma.pairofplayers.services;

import com.sirma.pairofplayers.exceptions.ResourceNotFoundException;
import com.sirma.pairofplayers.helpers.Constants;
import com.sirma.pairofplayers.helpers.CsvReader;
import com.sirma.pairofplayers.helpers.DateParser;
import com.sirma.pairofplayers.models.Match;
import com.sirma.pairofplayers.models.Team;
import com.sirma.pairofplayers.models.dtos.MatchDto;
import com.sirma.pairofplayers.repositories.MatchRepository;
import com.sirma.pairofplayers.repositories.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MatchService extends AbstractService<Match> {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    private static final String filepath = Constants.MATCH_FILE_PATH;

    public void saveMatchesFromCsvToDB() throws IOException {
        CsvReader csvReader = new CsvReader(filepath);
        List<String[]> rows = csvReader.readAll();
        csvReader.close();
        for (String[] row : rows) {
            Match match = new Match();
            List<Match> allMatches = matchRepository.findAll();
            long id = Long.parseLong(row[0]);
            if (doesIdExist(allMatches, id)) {
                continue;
            }
            Long aTeamId = Long.parseLong(row[1]);
            Long bTeamId = Long.parseLong(row[2]);
            match.setId(id);
            Team aTeamFromDb = teamRepository.findById(aTeamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", aTeamId));
            match.setATeam(aTeamFromDb);
            Team bTeamFromDb = teamRepository.findById(bTeamId)
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "teamId", bTeamId));
            match.setBTeam(bTeamFromDb);
            match.setDate(DateParser.parseDateWithMultipleFormats(row[3]));
            match.setScore(row[4]);
            matchRepository.save(match);
        }
    }

    public Page<MatchDto> getAll(Pageable pageable) {
        return this.matchRepository.findAllMatches(pageable);
    }

    public Match getMatch(Long matchId) {
        Match matchFromDb = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "matchId", matchId));
        return matchFromDb;
    }


    public MatchDto add(MatchDto matchDto) {
        Team aTeam = teamRepository.findById(matchDto.getaTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("aTeam", "aTeamId", matchDto.getaTeamId()));
        Team bTeam = teamRepository.findById(matchDto.getbTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("bTeam", "bTeamId", matchDto.getbTeamId()));

        Match match = new Match();
        match.setATeam(aTeam);
        match.setBTeam(bTeam);
        match.setDate(matchDto.getDate());
        match.setScore(matchDto.getScore());

        Match savedMatch = matchRepository.save(match);

        return mapToMatchDto(savedMatch);
    }

    public MatchDto updateMatch(Long matchId, MatchDto matchDto) {
        Match matchFromDb = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "matchId", matchId));

        Team aTeam = teamRepository.findById(matchDto.getaTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("aTeam", "aTeamId", matchDto.getaTeamId()));
        Team bTeam = teamRepository.findById(matchDto.getbTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("bTeam", "bTeamId", matchDto.getbTeamId()));

        matchFromDb.setATeam(aTeam);
        matchFromDb.setBTeam(bTeam);
        matchFromDb.setDate(matchDto.getDate());
        matchFromDb.setScore(matchDto.getScore());
        Match savedMatch = matchRepository.save(matchFromDb);
        return mapToMatchDto(savedMatch);
    }

    public MatchDto deleteMatch(Long matchId) {
        Match matchFromDb = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match", "matchId", matchId));

        matchRepository.deleteById(matchId);
        return mapToMatchDto(matchFromDb);
    }

    private MatchDto mapToMatchDto(Match match) {
        return new MatchDto(
                match.getATeam().getId(),
                match.getBTeam().getId(),
                match.getDate(),
                match.getScore()
        );
    }
}
