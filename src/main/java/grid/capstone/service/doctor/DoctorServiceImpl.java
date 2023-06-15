package grid.capstone.service.doctor;

import grid.capstone.model.Doctor;
import grid.capstone.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public Doctor getDoctor(Long doctorId) {

        //TODO: Add exception handling when id is not found
        Optional<Doctor> doctorOptional = doctorRepository.findById(doctorId);

        return doctorOptional.orElse(new Doctor());
    }
}
