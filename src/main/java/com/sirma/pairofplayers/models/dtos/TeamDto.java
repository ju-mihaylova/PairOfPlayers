package com.sirma.pairofplayers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamDto {
    private String name;
    private String managerFullName;
    private Character groupChar;
}
