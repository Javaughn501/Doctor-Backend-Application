package grid.capstone.service.patient;

import grid.capstone.dto.v1.PatientDTO;
import grid.capstone.model.Patient;
import org.springframework.http.HttpStatus;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

public interface PatientService {

    /**
     * Get a specific patient based on the
     * patientId passed
     *
     * @param patientId
     * @return A patient object
     * @throws ResourceNotFoundException when the id cannot be found
     */
    Patient getPatient(Long patientId);

    HttpStatus savePatient(PatientDTO patientDTO, Long doctorId);
}
