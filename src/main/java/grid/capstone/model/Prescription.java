package grid.capstone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.ToOne;

import java.time.LocalDate;
import java.util.function.Consumer;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medication;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dosage;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "medical_record_id")
    @JsonIgnore
    private MedicalRecord medicalRecord;


    public void updateObject(Prescription prescription) {
        updateHelper(prescription.getMedication(), this::setMedication);
        updateHelper(prescription.getStartDate(), this::setStartDate);
        updateHelper(prescription.getEndDate(), this::setEndDate);
        updateHelper(prescription.getDosage(), this::setDosage);
        updateHelper(prescription.getTotal(), this::setTotal);
    }

    private <T> void updateHelper(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }
}
