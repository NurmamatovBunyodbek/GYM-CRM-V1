package abdumalik.dev.gymcrmv1.repository;

import abdumalik.dev.gymcrmv1.model.Trainer;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TrainerRepo extends JpaRepository<Trainer, UUID> {
    Optional<Trainer> findByUsername(String username);
    boolean existsByEmail(@Email String email);
}