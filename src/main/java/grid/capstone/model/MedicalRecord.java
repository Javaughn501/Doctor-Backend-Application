package grid.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static grid.capstone.util.UpdateUtil.updateHelper;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
    @JsonIgnore
    private Patient patient;

    @OneToOne
    @JoinColumn(name = "appointment_id")
    private Appointment appointment;

    @OneToMany(mappedBy = "medicalRecord", cascade = CascadeType.PERSIST)
    private List<Prescription> prescriptions;

    public void updateObject(MedicalRecord medicalRecord) {
        updateHelper(medicalRecord.getCheckInDate(), this::setCheckInDate);
        updateHelper(medicalRecord.getNotes(), this::setNotes);
        updateHelper(medicalRecord.getDisease(), this::setDisease);
        updateHelper(medicalRecord.getStatus(), this::setStatus);
        updateHelper(medicalRecord.getRoomNo(), this::setRoomNo);
    }
}
