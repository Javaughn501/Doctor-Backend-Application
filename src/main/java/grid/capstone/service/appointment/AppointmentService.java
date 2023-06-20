package grid.capstone.service.appointment;

import grid.capstone.dto.v1.AppointmentDTO;
import grid.capstone.model.Appointment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Javaughn Stephenson
 * @since 20/06/2023
 */

public interface AppointmentService {

    /**
     * Creates a new appointment between the doctor and patient
     *
     * @param appointmentDTO DTO for the appointment object
     * @return
     */
    HttpStatus createAppointment(AppointmentDTO appointmentDTO);

    /**
     *
     * Returns the upcoming appointments of either the patient
     * or the doctor
     *
     * @param dateFilter
     * @param patientId
     * @param doctorId
     * @return
     */
    List<Appointment> getAllAppointments(Optional<LocalDate> dateFilter, Optional<Long> patientId, Optional<Long> doctorId);
}

