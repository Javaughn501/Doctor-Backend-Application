package grid.capstone.service.patient;

import grid.capstone.dto.v1.PatientSignUp;
import grid.capstone.mapper.PatientMapper;
import grid.capstone.mapper.PatientMapperImpl;
import grid.capstone.model.Doctor;
import grid.capstone.model.Patient;
import grid.capstone.repository.DoctorRepository;
import grid.capstone.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    PatientService patientService;

    @Mock
    PatientRepository patientRepository;
    @Mock
    DoctorRepository doctorRepository;

    PatientMapper patientMapper = new PatientMapperImpl();

    Patient testPatient;
    PatientSignUp testPatientDTO;
    Doctor testDoctor;

    @BeforeEach
    void setUp() {
        patientService = new PatientServiceImpl(patientRepository, doctorRepository, patientMapper, passwordEncoder);

        testPatient = Patient.builder()
                .email("test@gmail.com")
                .build();

        testPatientDTO = PatientSignUp.builder()
                .email("test@gmail.com")
                .password("test")
                .build();

        testDoctor = Doctor.builder().build();
    }

    @Test
    void getPatient() {
        given(patientRepository.findById(anyLong()))
                .willReturn(Optional.of(testPatient));

        Patient patient = patientService.getPatient(1L);

        assertThat(patient).isEqualTo(testPatient);
    }

    @Test
    void savePatient() {
        given(patientRepository.existsByEmail(anyString()))
                .willReturn(false);

        given(doctorRepository.existsById(anyLong()))
                .willReturn(true);

        HttpStatus httpStatus = patientService.savePatient(testPatientDTO, Optional.of(1L));

        then(patientRepository).should(times(1)).save(any(Patient.class));
        assertThat(httpStatus).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void getAllPatients() {
        given(doctorRepository.existsById(anyLong()))
                .willReturn(true);

        given(patientRepository.findAllByDoctorId(anyLong()))
                .willReturn(List.of(testPatient));


        List<Patient> allPatients = patientService.getAllPatients(1L);


        assertThat(allPatients).isEqualTo(List.of(testPatient));
    }
}