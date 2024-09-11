package com.sirma.pairofplayers.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class MatchDto {
    private Long aTeamId;
    private Long bTeamId;
    private LocalDate date;
    private String score;
    public Long getaTeamId() {
        return aTeamId;
    }

    public void setaTeamId(Long aTeamId) {
        this.aTeamId = aTeamId;
    }

    public Long getbTeamId() {
        return bTeamId;
    }

    public void setbTeamId(Long bTeamId) {
        this.bTeamId = bTeamId;
    }
}


