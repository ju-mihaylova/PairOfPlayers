package com.sirma.pairofplayers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDto {
    private Integer teamNumber;
    private String position;
    private String fullName;
    private Long teamId;
}
