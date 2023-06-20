package grid.capstone.service.appointment;

import grid.capstone.dto.v1.AppointmentDTO;
import grid.capstone.mapper.AppointmentMapper;
import grid.capstone.model.Appointment;
import grid.capstone.model.Doctor;
import grid.capstone.model.MedicalRecord;
import grid.capstone.model.Patient;
import grid.capstone.repository.AppointmentRepository;
import grid.capstone.repository.DoctorRepository;
import grid.capstone.repository.PatientRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * @author Javaughn Stephenson
 * @since 20/06/2023
 */

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentMapper appointmentMapper;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public AppointmentServiceImpl(AppointmentMapper appointmentMapper, PatientRepository patientRepository, DoctorRepository doctorRepository, AppointmentRepository appointmentRepository) {
        this.appointmentMapper = appointmentMapper;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public HttpStatus createAppointment(AppointmentDTO appointmentDTO) {
        //TODO:Sanitize Input

        Appointment appointment = appointmentMapper.toEntity(appointmentDTO);

        //TODO: Throw Exception if not exists
        List<Appointment> patientAppointments = appointmentRepository.findByPatient(Patient.builder().id(appointmentDTO.getPatientId()).build());
        List<Appointment> doctorAppointments = appointmentRepository.findByDoctor(Doctor.builder().id(appointmentDTO.getDoctorId()).build());


        //Check if any existing appointments is clashing with the new one
        boolean patientMatch = patientAppointments.stream()
                .filter(app -> app.getAppointmentDate().equals(appointment.getAppointmentDate()))
                .anyMatch(app -> isAppointmentClashing(app, appointment));

        boolean doctorMatch = doctorAppointments.stream()
                .filter(app -> app.getAppointmentDate().equals(appointment.getAppointmentDate()))
                .anyMatch(app -> isAppointmentClashing(app, appointment));


        //If any matches, return a conflict
        if (patientMatch || doctorMatch)
            return HttpStatus.CONFLICT;

        appointmentRepository.save(appointment);

        return HttpStatus.CREATED;
    }


    @Override
    public List<Appointment> getAllAppointments(Optional<LocalDate> dateFilter, Optional<Long> patientId, Optional<Long> doctorId) {

        Specification<Appointment> appointmentSpecification =
                Specification
                        .where(dateFilter
                                .map(AppointmentSpecification::hasDate)
                                .orElse(null)
                        );

        if (doctorId.isPresent())
            appointmentSpecification = appointmentSpecification
                    .and(doctorId
                            .map(AppointmentSpecification::hasDoctor)
                            .orElse(null)
                    )
                    .and((root, query, criteriaBuilder) ->
                            criteriaBuilder.greaterThanOrEqualTo(
                                    root.get("appointmentDate"),
                                    LocalDate.now()
                            )
                    );
        else if(patientId.isPresent())
            appointmentSpecification = appointmentSpecification
                    .and(patientId
                            .map(AppointmentSpecification::hasPatient)
                            .orElse(null)
                    );
        //TODO: Throw error if one is not entered




        return appointmentRepository.findAll(appointmentSpecification);
    }













    //Checks to see if any conflict between two given appointments
    private Boolean isAppointmentClashing(Appointment appointment1, Appointment appointment2) {
        return appointment1.getStartTime().isBefore(appointment2.getEndTime())
                && appointment2.getStartTime().isBefore(appointment1.getEndTime());
    }

}
