package grid.capstone.service.appointment;

import grid.capstone.dto.v1.AppointmentDTO;
import grid.capstone.mapper.AppointmentMapper;
import grid.capstone.model.Appointment;
import grid.capstone.repository.AppointmentRepository;
import grid.capstone.repository.DoctorRepository;
import grid.capstone.repository.PatientRepository;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
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

        /*
        If any appointment has a conflict then return conflict
         */
        if (Boolean.TRUE.equals(hasAppointmentConflict(appointment))){
            return HttpStatus.CONFLICT;
        }

        appointmentRepository.save(appointment);

        return HttpStatus.CREATED;
    }


    @Override
    public List<Appointment> getAllAppointments(Optional<LocalDate> dateFilter, Optional<Long> patientId, Optional<Long> doctorId) {

        //Add the date filter if client added in req param
        Specification<Appointment> appointmentSpecification =
                Specification
                        .where(dateFilter
                                .map(AppointmentSpecification::hasDate)
                                .orElse(null)
                        );

        //If doctor id is present filter based off the doctor id if not patient
        //To avoid
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


    @Override
    public HttpStatus updateAppointment(Long appointmentId, AppointmentDTO appointmentDTO) {

        //TODO: Sanitize input
        Appointment updatedAppointment = appointmentMapper.toEntity(appointmentDTO);


        //TODO: Add exception handling when id is not found
        Appointment appointment = appointmentRepository.findById(appointmentId).get();


        //Update Values in the object if not null
        appointment.updateObject(updatedAppointment);

        if (Boolean.TRUE.equals(hasAppointmentConflict(appointment))){
            return HttpStatus.CONFLICT;
        }


        appointmentRepository.save(appointment);

        return HttpStatus.OK;
    }


    private Boolean hasAppointmentConflict(Appointment appointment) {

        //TODO: Throw Exception if not exists
        List<Appointment> patientAppointments = appointmentRepository.findByPatient(appointment.getPatient());
        List<Appointment> doctorAppointments = appointmentRepository.findByDoctor(appointment.getDoctor());


        //Check if any existing appointments is clashing with the new one
        boolean patientMatch = patientAppointments.stream()
                .filter(app -> !Objects.equals(app.getId(), appointment.getId()))
                .filter(app -> app.getAppointmentDate().equals(appointment.getAppointmentDate()))
                .anyMatch(app -> isAppointmentClashing(app, appointment));

        boolean doctorMatch = doctorAppointments.stream()
                .filter(app -> !Objects.equals(app.getId(), appointment.getId()))
                .filter(app -> app.getAppointmentDate().equals(appointment.getAppointmentDate()))
                .anyMatch(app -> isAppointmentClashing(app, appointment));

        /*
        If any is true, there's a conflict in the appointment
        Only return false when both are false.
         */
        return patientMatch || doctorMatch;
    }

    //Checks to see if any conflict between two given appointments
    private Boolean isAppointmentClashing(Appointment appointment1, Appointment appointment2) {
        return appointment1.getStartTime().isBefore(appointment2.getEndTime())
                && appointment2.getStartTime().isBefore(appointment1.getEndTime());
    }

}
