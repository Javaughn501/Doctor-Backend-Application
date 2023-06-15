package grid.capstone.repository;

import grid.capstone.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Javaughn Stephenson
 * @since 15/06/2023
 */

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
