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
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

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

    @Override
    public HttpStatus updateMedicalRecord(Long recordId, MedicalRecordDTO medicalRecordDTO) {

        MedicalRecord updatedMedicalRecord = medicalMapper.toEntity(medicalRecordDTO);

        //TODO: Throw Exception when id is not found
        MedicalRecord medicalRecord = medicalRepository.findById(recordId).get();

        //Update the existing entity with the new fields
        medicalRecord.updateObject(updatedMedicalRecord);

        //List of the updated prescriptions - Updated the medical record incase any new
        //Prescriptions were added
        List<Prescription> updatedPrescriptions = updatedMedicalRecord.getPrescriptions().stream()
                .map(prescription -> {
                    prescription.setMedicalRecord(medicalRecord);
                    return prescription;
                })
                .toList();

        if (!updatedPrescriptions.isEmpty()) {

            //Get the prescriptions that exists in the DB
            List<Prescription> existingPrescriptions = prescriptionRepository.findAllById(
                    updatedMedicalRecord.getPrescriptions().stream()
                            .map(Prescription::getId)
                            .toList()
            );


            //Save the updated prescriptions
            prescriptionRepository.saveAll(
                updatePrescriptions(
                        updatedPrescriptions,
                        existingPrescriptions
                )
            );

        }

        return HttpStatus.OK;
    }


    /*
    Method is used to update the prescriptions in the medical record when a
    put request with the updated medical records has been sent
     */
    private List<Prescription> updatePrescriptions(
            List<Prescription> updatedPrescriptions,
            List<Prescription> existingPrescriptions
            ) {


        //Update the prescriptions that exists in the DB
        existingPrescriptions = existingPrescriptions.stream()
                //Updating the prescriptions that exists
                .map(prescription -> {
                    //Getting the updated prescription from the list
                    Optional<Prescription> optional = updatedPrescriptions.stream().filter(updated -> updated.getId().equals(prescription.getId())).findFirst();
                    if (optional.isPresent()) {
                        //Updating the prescription that was retrieved from the database
                        prescription.updateObject(optional.get());
                    }
                    return prescription;
                })
                .toList();

        //New Prescriptions that don't exist in the database as yet
        List<Prescription> newPrescriptions = updatedPrescriptions.stream()
                .filter(Predicate.not(existingPrescriptions::contains))
                .toList();

        return Stream.concat(existingPrescriptions.stream(), newPrescriptions.stream())
                .toList();

    }

}
