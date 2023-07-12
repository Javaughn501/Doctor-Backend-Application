package grid.capstone.service.patient;

import grid.capstone.dto.v1.PatientDTO;
import grid.capstone.exception.ResourceNotFoundException;
import grid.capstone.mapper.PatientMapper;
import grid.capstone.model.Doctor;
import grid.capstone.model.Patient;
import grid.capstone.repository.DoctorRepository;
import grid.capstone.repository.PatientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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


        return patientRepository.findById(patientId)
                .orElseThrow(() -> new ResourceNotFoundException("Patient with id " + patientId + " does not exist"));
    }

    @Override
    public HttpStatus savePatient(PatientDTO patientDTO, Long doctorId) {
        //Check if doctor exists
        if (doctorId != null && !doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor with id " + doctorId + " does not exist");
        }

        if (patientRepository.existsByEmail(patientDTO.getEmail())) {
            throw new ResourceNotFoundException("Email already exists");
        }


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
        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor with id " + doctorId + " does not exist");
        }


        return patientRepository
                .findAllByDoctorId(doctorId);
    }
}
