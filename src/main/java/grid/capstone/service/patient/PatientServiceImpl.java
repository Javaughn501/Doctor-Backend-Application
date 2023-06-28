package grid.capstone.service.patient;

import grid.capstone.dto.v1.PatientDTO;
import grid.capstone.mapper.PatientMapper;
import grid.capstone.model.Doctor;
import grid.capstone.model.Patient;
import grid.capstone.repository.DoctorRepository;
import grid.capstone.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PatientMapper patientMapper;

    public PatientServiceImpl(PatientRepository patientRepository, DoctorRepository doctorRepository, PatientMapper patientMapper) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.patientMapper = patientMapper;
    }

    @Override
    public Patient getPatient(Long patientId) {

        //TODO: Add exception handling when id is not found
        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        return patientOptional.orElse(null);
    }

    @Override
    public HttpStatus savePatient(PatientDTO patientDTO, Long doctorId) {
        //Check if doctor exists
        doctorRepository.existsById(doctorId);
        //TODO: Throw exception if doctor doesnt exists.



        //TODO: Sanitize input
        //TODO: Throw Exception if patient email exists

        //Mapping the DTO to the entity and setting the doctor
        //to the entity
        Patient patient = patientMapper.toEntity(patientDTO);
        patient.setDoctor(
                Doctor.builder()
                        .id(doctorId)
                        .build()
        );

        patientRepository.save(patient);

        return HttpStatus.CREATED;
    }

    @Override
    public List<Patient> getAllPatients(Long doctorId) {
        //TODO: throw exception if doctorId doesnt exists

        return patientRepository
                .findAllByDoctor(Doctor.builder().id(doctorId).build());
    }
}
