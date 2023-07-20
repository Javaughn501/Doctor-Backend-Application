package grid.capstone.mapper;

import grid.capstone.dto.v1.AppointmentDTO;
import grid.capstone.model.Appointment;
import grid.capstone.model.Doctor;
import grid.capstone.model.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;


class AppointmentMapperTest {

//    @Autowired
//    AppointmentMapper appointmentMapper;

    Appointment appointment;
    AppointmentDTO appointmentDTO;

    @BeforeEach
    void setUp() {

        appointment = Appointment.builder()
                .reason("Reason")
                .appointmentDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .patient(
                        Patient.builder()
                                .id(1L)
                                .build()
                )
                .doctor(
                        Doctor.builder()
                                .id(1L)
                                .build()
                )
                .build();


        appointmentDTO = AppointmentDTO.builder()
                .doctorId(1L)
                .patientId(1L)
                .reason("reason")
                .appointmentDate(LocalDate.now())
                .startTime(LocalTime.now())
                .endTime(LocalTime.now())
                .build();

    }

    @Test
    void toDTO() {

        AppointmentDTO dto = AppointmentMapper.INSTANCE.toDTO(appointment);


        assertThat(dto).isInstanceOf(AppointmentDTO.class);
        assertThat(dto.getAppointmentDate()).isEqualTo(appointment.getAppointmentDate());

    }

    @Test
    void toEntity() {

        Appointment entity = AppointmentMapper.INSTANCE.toEntity(appointmentDTO);

        assertThat(entity).isInstanceOf(Appointment.class);
        assertThat(entity.getAppointmentDate()).isEqualTo(appointmentDTO.getAppointmentDate());

    }
}