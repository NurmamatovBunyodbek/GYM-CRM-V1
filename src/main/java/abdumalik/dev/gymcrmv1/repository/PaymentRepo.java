package abdumalik.dev.gymcrmv1.repository;

import abdumalik.dev.gymcrmv1.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepo extends JpaRepository<Payment, UUID> {
}