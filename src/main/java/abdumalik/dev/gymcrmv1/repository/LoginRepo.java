package abdumalik.dev.gymcrmv1.repository;

import abdumalik.dev.gymcrmv1.model.Login;
import jakarta.validation.constraints.Email;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoginRepo extends JpaRepository<Login, UUID> {
    boolean existsByEmail(@Email String email);
    boolean existsByUsername(String username);
}