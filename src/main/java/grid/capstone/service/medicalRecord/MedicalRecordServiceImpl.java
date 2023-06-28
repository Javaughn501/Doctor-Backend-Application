package grid.capstone.service.medicalRecord;

import grid.capstone.dto.v1.MedicalRecordDTO;
import grid.capstone.mapper.MedicalRecordMapper;
import grid.capstone.model.Appointment;
import grid.capstone.model.MedicalRecord;
import grid.capstone.model.Patient;
import grid.capstone.model.Prescription;
import grid.capstone.repository.AppointmentRepository;
import grid.capstone.repository.MedicalRecordRepository;
import grid.capstone.repository.PrescriptionRepository;
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
    private final MedicalRecordMapper medicalMapper;

    private final PrescriptionRepository prescriptionRepository;
    private final AppointmentRepository appointmentRepository;

    public MedicalRecordServiceImpl(MedicalRecordRepository medicalRepository, MedicalRecordMapper medicalMapper, PrescriptionRepository prescriptionRepository, AppointmentRepository appointmentRepository) {
        this.medicalRepository = medicalRepository;
        this.medicalMapper = medicalMapper;
        this.prescriptionRepository = prescriptionRepository;
        this.appointmentRepository = appointmentRepository;
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

        //Change the DTO to the entity
        MedicalRecord medicalRecord = medicalMapper.toEntity(medicalRecordDTO);

        //TODO: Throw Exception when id doesnt exists
        //Get the appointment id from the entity passed and look it up in the DB
        Appointment appointment = appointmentRepository.findById(medicalRecord.getAppointment().getId()).get();

        //Get the list of prescriptions from the medical record
        List<Prescription> prescriptions = medicalRecord.getPrescriptions();


        MedicalRecord savedMedicalRecord = medicalRepository.save(medicalRecord);

        //After saving the medical record update the list of prescriptions
        //With the medical record ID for the relationship when saved
        prescriptions
                .forEach(prescription -> prescription.setMedicalRecord(savedMedicalRecord));
        prescriptionRepository.saveAll(prescriptions);

        //Update the appointment record with the medical record
        appointment.setMedicalRecord(savedMedicalRecord);
        appointmentRepository.save(appointment);

        return HttpStatus.CREATED;
    }
}
