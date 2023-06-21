package grid.capstone.service.medicalRecord;

import grid.capstone.dto.v1.MedicalRecordDTO;
import grid.capstone.model.MedicalRecord;
import grid.capstone.model.Patient;
import grid.capstone.repository.MedicalRecordRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Javaughn Stephenson
 * @since 21/06/2023
 */

@Service
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final MedicalRecordRepository medicalRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRepository) {
        this.medicalRepository = medicalRepository;
    }


    @Override
    public List<MedicalRecord> getMedicalRecords(Long patientId) {
        //TODO: Throw Exception when id doesnt exists

        return medicalRepository.findAllByPatient(
                Patient.builder().id(patientId).build()
        );
    }

    @Override
    public HttpStatus createMedicalRecord(Long patientId, MedicalRecordDTO medicalRecordDTO) {
        return null;
    }

    @Override
    public HttpStatus updateMedicalRecord(Long recordId, MedicalRecordDTO medicalRecordDTO) {
        return null;
    }
}
