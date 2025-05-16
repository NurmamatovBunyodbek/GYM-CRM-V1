package abdumalik.dev.gymcrmv1.service;

import abdumalik.dev.gymcrmv1.dto.TrainerDto;
import abdumalik.dev.gymcrmv1.model.Result;
import abdumalik.dev.gymcrmv1.model.Trainer;
import abdumalik.dev.gymcrmv1.model.User;
import abdumalik.dev.gymcrmv1.repository.TrainerRepo;
import abdumalik.dev.gymcrmv1.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TrainerService {

    @Autowired
    TrainerRepo repo;

    @Autowired
    UserRepo userRepo;

    public List<Trainer> getAllTrainers() {
        return repo.findAll();
    }

    public Trainer getTrainerById(UUID id) {
        return repo.findById(id).get();
    }

    public Optional<Trainer> getTrainerByUsername(String username) {
        return repo.findByUsername(username);
    }

    public Result create(TrainerDto dto) {
        boolean b = repo.existsByEmail(dto.getEmail());
        if (b) {
            return new Result("Trainer info created successfully", true);
        }

        Trainer trainer = new Trainer();
        trainer.setFirstName(dto.getFirstName());
        trainer.setLastName(dto.getLastName());
        trainer.setUsername(dto.getUsername());
        trainer.setEmail(dto.getEmail());
        trainer.setPassword(dto.getPassword());
        trainer.setPrice(dto.getPrice());

        Optional<User> byId = userRepo.findById(dto.getUserId());
        User user = byId.get();
        trainer.setUserId(user);

        repo.save(trainer);
        return new Result("Trainer info created successfully", true);
    }

    public Result update(UUID id, TrainerDto dto) {
        Optional<Trainer> byId = repo.findById(id);
        if (byId.isPresent()) {
            Trainer trainer = byId.get();
            trainer.setFirstName(dto.getFirstName());
            trainer.setLastName(dto.getLastName());
            trainer.setUsername(dto.getUsername());
            trainer.setEmail(dto.getEmail());
            trainer.setPassword(dto.getPassword());
            trainer.setPrice(dto.getPrice());
            repo.save(trainer);
            return new Result("Trainer info updated successfully", true);
        }
        return new Result("Trainer info not found", false);
    }

    public Result delete(UUID id) {
        Optional<Trainer> byId = repo.findById(id);
        if (byId.isPresent()) {
            repo.delete(byId.get());
            return new Result("Trainer info deleted successfully", true);
        }
        return new Result("Trainer info not found", false);
    }

}