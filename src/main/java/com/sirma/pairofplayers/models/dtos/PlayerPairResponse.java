package com.sirma.pairofplayers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PlayerPairResponse {
    private Long playerId1;
    private Long playerId2;
    private Integer totalCommonMinutes;
    private List<MatchDuration> matchDurations;
}
