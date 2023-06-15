package grid.capstone.service.patient;

import grid.capstone.model.Patient;

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

}
