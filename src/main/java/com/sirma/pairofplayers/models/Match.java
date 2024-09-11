package com.sirma.pairofplayers.models;

import com.sirma.pairofplayers.interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "matches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Match implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "a_team_id", referencedColumnName = "id", nullable = false)
    private Team aTeam;

    @ManyToOne
    @JoinColumn(name = "b_team_id", referencedColumnName = "id", nullable = false)
    private Team bTeam;

    @Past(message = "Date should be in the past")
    private LocalDate date;

    @Size(min = 3, max = 255, message = "Score should be between 3 and 255 characters long")
    private String score;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
