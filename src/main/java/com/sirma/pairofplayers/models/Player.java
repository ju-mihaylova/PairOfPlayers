package com.sirma.pairofplayers.models;

import com.sirma.pairofplayers.interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "players")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Player implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Digits(integer = 2, fraction = 0, message = "Team number should be an integer")
    private Integer teamNumber;

    @Size(min = 2, max = 2, message = "Position should be 2 characters long")
    private String position;

    @Size(min = 3, max = 255, message = "Full name should be between 3 and 255 characters long")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
    private Team team;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;

}
