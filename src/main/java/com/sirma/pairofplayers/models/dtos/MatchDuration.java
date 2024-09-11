package com.sirma.pairofplayers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MatchDuration {
    private Long matchId;
    private Integer commonMatchMinutes;
}
