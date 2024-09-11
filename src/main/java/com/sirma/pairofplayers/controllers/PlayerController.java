package com.sirma.pairofplayers.controllers;

import com.sirma.pairofplayers.models.Player;
import com.sirma.pairofplayers.models.dtos.PlayerDto;
import com.sirma.pairofplayers.services.PlayerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/players")
@Controller
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping(path="/")
    public @ResponseBody ResponseEntity<Page<PlayerDto>> getAllPlayers(Pageable pageable) {
        return new ResponseEntity<>(playerService.getAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        return new ResponseEntity<>(playerService.getPlayer(playerId), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<PlayerDto> addPlayer(@Valid @RequestBody PlayerDto playerDto) {
        return new ResponseEntity<>(playerService.add(playerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{playerId}")
    public ResponseEntity<PlayerDto> updatePlayer(
            @Valid
            @PathVariable Long playerId,
            @RequestBody PlayerDto playerDto) {
        return new ResponseEntity<>(playerService.updatePlayer(playerId, playerDto), HttpStatus.OK);
    }

    @DeleteMapping("/{playerId}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable Long playerId) {
        return new ResponseEntity<>(playerService.deletePlayer(playerId), HttpStatus.OK);
    }
}
