package abdumalik.dev.gymcrmv1.service;

import abdumalik.dev.gymcrmv1.dto.PaymentDto;
import abdumalik.dev.gymcrmv1.model.Payment;
import abdumalik.dev.gymcrmv1.model.Result;
import abdumalik.dev.gymcrmv1.repository.PaymentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    PaymentRepo repo;

    public List<Payment> getAllPayments() {
        return repo.findAll();
    }

    public Payment getPaymentById(UUID id) {
        return repo.findById(id).get();
    }

    public Result create(PaymentDto dto) {
        Payment payment = new Payment();
        payment.setName(dto.getName());
        payment.setMonthsPaid(dto.getMonthsPaid());
        payment.setPricePerMonth(dto.getPricePerMonth());
        repo.save(payment);
        return new Result("Payment info created successfully", true);
    }

    public Result update(UUID id, PaymentDto dto) {
        Optional<Payment> byId = repo.findById(id);
        if (byId.isPresent()) {
            Payment payment = byId.get();
            payment.setName(dto.getName());
            payment.setMonthsPaid(dto.getMonthsPaid());
            payment.setPricePerMonth(dto.getPricePerMonth());
            repo.save(payment);
            return new Result("Payment info updated successfully", true);
        }
        return new Result("Payment info not found", false);
    }

    public Result delete(UUID id) {
        Optional<Payment> byId = repo.findById(id);
        if (byId.isPresent()) {
            repo.deleteById(id);
            return new Result("Payment info deleted successfully", true);
        }
        return new Result("Payment info not found", false);
    }

}