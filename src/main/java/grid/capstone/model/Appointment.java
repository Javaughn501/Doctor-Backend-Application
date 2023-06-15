package grid.capstone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    private MedicalRecord medicalRecord;

}
