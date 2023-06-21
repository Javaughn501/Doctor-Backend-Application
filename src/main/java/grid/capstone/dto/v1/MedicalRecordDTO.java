package grid.capstone.dto.v1;

import grid.capstone.model.Prescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 21/06/2023
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordDTO {

    private Long doctorId;
    private Long patientId;
    private Long appointmentId;
    private LocalDate checkInDate;
    private String notes;
    private String disease;
    private String status;
    private String roomNo;
    private List<Prescription> prescriptions;

}
