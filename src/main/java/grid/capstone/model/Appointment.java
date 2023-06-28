package grid.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static grid.capstone.util.UpdateUtil.updateHelper;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@ToString(exclude = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonIgnore
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    @JsonIgnore
    private Patient patient;

    @OneToOne
    @JsonIgnore
    private MedicalRecord medicalRecord;


    public void updateObject(Appointment appointment) {
        updateHelper(appointment.getAppointmentDate(), this::setAppointmentDate);
        updateHelper(appointment.getStartTime(), this::setStartTime);
        updateHelper(appointment.getEndTime(), this::setEndTime);
        updateHelper(appointment.getReason(), this::setReason);
        updateHelper(appointment.getMedicalRecord(), this::setMedicalRecord);
    }

}
