package com.sirma.pairofplayers.controllers;

import com.sirma.pairofplayers.models.Match;
import com.sirma.pairofplayers.models.dtos.MatchDto;
import com.sirma.pairofplayers.services.MatchService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/matches")
@Controller
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping(path="/")
    public @ResponseBody ResponseEntity<Page<MatchDto>> getAllMatches(Pageable pageable) {
        return new ResponseEntity<>(matchService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{matchId}")
    public ResponseEntity<Match> getMatch(@PathVariable Long matchId) {
        return new ResponseEntity<>(matchService.getMatch(matchId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<MatchDto> addMatch(@Valid @RequestBody MatchDto matchDto) {
        return new ResponseEntity<>(matchService.add(matchDto), HttpStatus.CREATED);
    }

    @PutMapping("/{matchId}")
    public ResponseEntity<MatchDto> updateMatch(
            @Valid
            @PathVariable Long matchId,
            @RequestBody MatchDto matchDto) {
        return new ResponseEntity<>(matchService.updateMatch(matchId, matchDto), HttpStatus.OK);
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<MatchDto> deleteMatch(@PathVariable Long matchId) {
        return new ResponseEntity<>(matchService.deleteMatch(matchId), HttpStatus.OK);
    }
}
