package abdumalik.dev.gymcrmv1.service;

import abdumalik.dev.gymcrmv1.dto.UserDto;
import abdumalik.dev.gymcrmv1.model.Payment;
import abdumalik.dev.gymcrmv1.model.Result;
import abdumalik.dev.gymcrmv1.model.User;
import abdumalik.dev.gymcrmv1.repository.PaymentRepo;
import abdumalik.dev.gymcrmv1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserRepo repo;

    @Autowired
    PaymentRepo paymentRepo;

    public List<User> findAll() {
        return repo.findAll();
    }

    public User findById(UUID id) {
        return repo.findById(id).get();
    }

    public Optional<User> findByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Result create(UserDto dto) {
        boolean b = repo.existsByEmail(dto.getEmail());
        if (b) {
            return new Result("Email is already exist", false);
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setVisitCount(dto.getVisitCount() + 1);
        user.setHasPaid(dto.isHasPaid());

        Optional<Payment> byId = paymentRepo.findById(dto.getPaymentId());
        Payment payment = byId.get();
        user.setPayment(payment);

        repo.save(user);
        return new Result("User info saved successfully", true);
    }

    public Result update(UserDto dto, UUID id) {
        Optional<User> byId = repo.findById(id);
        if (byId.isPresent()) {
            User user = byId.get();
            user.setFirstName(dto.getFirstName());
            user.setLastName(dto.getLastName());
            user.setUsername(dto.getUsername());
            user.setEmail(dto.getEmail());
            user.setPassword(dto.getPassword());
            user.setVisitCount(dto.getVisitCount() + 1);
            user.setHasPaid(dto.isHasPaid());

            Optional<Payment> byPaymentId = paymentRepo.findById(dto.getPaymentId());
            Payment payment = byPaymentId.get();
            user.setPayment(payment);

            repo.save(user);
            return new Result("User info updated successfully", true);
        }
        return new Result("User info not found", false);
    }

    public Result delete(UUID id) {
        Optional<User> byId = repo.findById(id);
        if (byId.isPresent()) {
            repo.delete(byId.get());
            return new Result("User info deleted successfully", true);
        }
        return new Result("User info not found", false);
    }

}