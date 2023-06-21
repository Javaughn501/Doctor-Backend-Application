package grid.capstone.controller;

import grid.capstone.model.MedicalRecord;
import grid.capstone.service.medicalRecord.MedicalRecordService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 21/06/2023
 */

@RestController
@RequestMapping("/api/v1/medical-record")
public class MedicalRecordController {

    private final MedicalRecordService medicalService;

    public MedicalRecordController(MedicalRecordService medicalService) {
        this.medicalService = medicalService;
    }

    @GetMapping("/{patientId}")
    public List<MedicalRecord> getMedicalRecords(@PathVariable Long patientId) {
        return medicalService.getMedicalRecords(patientId);
    }


}
