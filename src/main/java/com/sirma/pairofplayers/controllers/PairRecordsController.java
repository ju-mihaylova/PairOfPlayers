package com.sirma.pairofplayers.controllers;

import com.sirma.pairofplayers.models.dtos.PlayerPairResponse;
import com.sirma.pairofplayers.services.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PairRecordsController {
    private final RecordService recordService;

    public PairRecordsController(RecordService recordService) {
        this.recordService = recordService;
    }

    @GetMapping("/pairs")
    public ResponseEntity<List<PlayerPairResponse>> getPlayerPairs() {
        List<PlayerPairResponse> playerPairs = recordService.getPairPlayers();
        return new ResponseEntity<>(playerPairs, HttpStatus.OK);
    }

}
