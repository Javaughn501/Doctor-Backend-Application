package grid.capstone.service.appointment;

import grid.capstone.dto.v1.AppointmentDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

/**
 * @author Javaughn Stephenson
 * @since 20/06/2023
 */

public interface AppointmentService {

    HttpStatus createAppointment(AppointmentDTO appointmentDTO);
}
