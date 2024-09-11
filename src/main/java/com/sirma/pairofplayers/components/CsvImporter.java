package com.sirma.pairofplayers.components;

import com.sirma.pairofplayers.services.MatchService;
import com.sirma.pairofplayers.services.PlayerService;
import com.sirma.pairofplayers.services.RecordService;
import com.sirma.pairofplayers.services.TeamService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class CsvImporter {
    private final PlayerService playerService;
    private final TeamService teamService;
    private final MatchService matchService;
    private final RecordService recordService;

    public CsvImporter(
            PlayerService playerService,
            TeamService teamService,
            MatchService matchService,
            RecordService recordService
    ) {
        this.playerService = playerService;
        this.teamService = teamService;
        this.matchService = matchService;
        this.recordService = recordService;
    }

    @ShellMethod
    public String runCsvImporter() throws Exception {
        System.out.println("Processing CSV files");
        teamService.saveTeamsFromCsvToDB();
        playerService.savePlayersFromCsvToDB();
        matchService.saveMatchesFromCsvToDB();
        recordService.saveRecordsFromCsvToDB();

        return "CSV files processed successfully";
    }
}
