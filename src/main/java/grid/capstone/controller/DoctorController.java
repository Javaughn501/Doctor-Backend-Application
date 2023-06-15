package grid.capstone.controller;

import grid.capstone.model.Doctor;
import grid.capstone.service.doctor.DoctorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{doctorId}")
    public Doctor getDoctor(@PathVariable Long doctorId) {
        return doctorService.getDoctor(doctorId);
    }

}
