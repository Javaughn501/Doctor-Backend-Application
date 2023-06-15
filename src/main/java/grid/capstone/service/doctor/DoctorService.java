package grid.capstone.service.doctor;

import grid.capstone.model.Doctor;


/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

public interface DoctorService {


    /**
     * This method searches for a specific
     * doctor based on the id passed
     *
     * @param doctorId
     * @return A doctor object
     * @throws  ResourceNotFoundException when the id is not found
     */
    Doctor getDoctor(Long doctorId);
}
