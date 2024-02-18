package api.com.medhead.users.repository;

import api.com.medhead.users.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {
    Patient findByEmail(String email);

    Patient findByUserId(int userId);
}
