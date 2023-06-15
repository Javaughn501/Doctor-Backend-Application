package grid.capstone.service.patient;

import grid.capstone.model.Patient;
import grid.capstone.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Patient getPatient(Long patientId) {

        //TODO: Add exception handling when id is not found
        Optional<Patient> patientOptional = patientRepository.findById(patientId);

        return patientOptional.orElse(new Patient());
    }
}
