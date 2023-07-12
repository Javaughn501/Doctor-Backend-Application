package grid.capstone.controller;

import grid.capstone.dto.v1.PatientDTO;
import grid.capstone.model.Patient;
import grid.capstone.service.patient.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public List<Patient> getAllPatients(@RequestParam(required = true) Long doctorId) {
        return patientService.getAllPatients(doctorId);
    }


    @GetMapping("/{patientId}")
    public Patient getPatient(@PathVariable Long patientId) {
        return patientService.getPatient(patientId);
    }

    @PostMapping("/{doctorId}")
    public ResponseEntity<HttpStatus> postPatient(@PathVariable Long doctorId,
                                                  @Valid @RequestBody PatientDTO patientDTO
                                                  ) {
        return ResponseEntity
                .status(patientService.savePatient(patientDTO, doctorId))
                .build();
    }



}
