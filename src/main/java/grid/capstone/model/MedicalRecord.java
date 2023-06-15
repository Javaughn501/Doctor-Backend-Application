package grid.capstone.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@Entity
public class MedicalRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkInDate;
    private String notes;
    private String disease;
    private String status;
    private String roomNo;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;
}
