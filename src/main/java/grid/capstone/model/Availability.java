package grid.capstone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@Entity
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne(cascade = CascadeType.ALL)
    private Doctor doctor;
}
