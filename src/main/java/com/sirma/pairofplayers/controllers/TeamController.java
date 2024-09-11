package com.sirma.pairofplayers.controllers;


import com.sirma.pairofplayers.models.Team;
import com.sirma.pairofplayers.models.dtos.TeamDto;
import com.sirma.pairofplayers.services.TeamService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/teams")
@Controller
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(path="/")
    public @ResponseBody ResponseEntity<Page<TeamDto>> getAllTeams(Pageable pageable) {
        return new ResponseEntity<>(teamService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeam(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.getTeam(teamId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<TeamDto> addTeam(@Valid @RequestBody TeamDto teamDto) {
        return new ResponseEntity<>(teamService.add(teamDto), HttpStatus.CREATED);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<TeamDto> updateTeam(
            @Valid
            @PathVariable Long teamId,
            @RequestBody TeamDto teamDto) {
        return new ResponseEntity<>(teamService.updateTeam(teamId, teamDto), HttpStatus.OK);
    }

    @DeleteMapping("/{teamId}")
    public ResponseEntity<TeamDto> deleteTeam(@PathVariable Long teamId) {
        return new ResponseEntity<>(teamService.deleteTeam(teamId), HttpStatus.OK);
    }
}
