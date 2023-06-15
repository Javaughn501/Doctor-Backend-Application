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
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String medication;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dosage;
    private Double total;

    @OneToOne
    private MedicalRecord medicalRecord;
}
