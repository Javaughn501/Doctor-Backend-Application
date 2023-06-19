package grid.capstone.controller;

import grid.capstone.model.Patient;
import grid.capstone.service.patient.PatientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@RestController
@RequestMapping("api/v1/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/{patientId}")
    public Patient getPatient(@PathVariable Long patientId) {
        return patientService.getPatient(patientId);
    }

}
