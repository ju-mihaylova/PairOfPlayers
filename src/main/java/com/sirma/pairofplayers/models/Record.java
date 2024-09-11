package com.sirma.pairofplayers.models;

import com.sirma.pairofplayers.interfaces.Identifiable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "records")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Record implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player_id", referencedColumnName = "id", nullable = false)
    private Player player;

    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id", nullable = false)
    private Match match;


    @Digits(integer = 3, fraction = 0, message = "From minutes should be an integer")
    private Integer fromMinutes;

    @Digits(integer = 3, fraction = 0, message = "To minutes should be an integer or null")
    private Integer toMinutes;

    @CreationTimestamp
    private LocalDateTime createdOn;

    @UpdateTimestamp
    private LocalDateTime updatedOn;
}
