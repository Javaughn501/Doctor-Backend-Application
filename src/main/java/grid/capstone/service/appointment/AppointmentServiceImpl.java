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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
                .anyMatch(app -> isAppointmentClashing(app, appointment));

        boolean doctorMatch = doctorAppointments.stream()
                .anyMatch(app -> isAppointmentClashing(app, appointment));


        //If any matches, return a conflict
        if (patientMatch || doctorMatch)
            return HttpStatus.CONFLICT;

        appointmentRepository.save(appointment);

        return HttpStatus.CREATED;
    }


    //Checks to see if any conflict between two given appointments
    private Boolean isAppointmentClashing(Appointment appointment1, Appointment appointment2) {
        return appointment1.getStartTime().isBefore(appointment2.getEndTime())
                && appointment2.getStartTime().isBefore(appointment1.getEndTime());
    }

}
