package grid.capstone.controller;

import grid.capstone.dto.v1.AppointmentDTO;
import grid.capstone.service.appointment.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javaughn Stephenson
 * @since 20/06/2023
 */

@RestController
@RequestMapping("/api/v1/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity
                .status(appointmentService.createAppointment(appointmentDTO))
                .build();
    }

}
