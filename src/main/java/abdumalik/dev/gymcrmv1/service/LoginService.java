package abdumalik.dev.gymcrmv1.service;

import abdumalik.dev.gymcrmv1.dto.LoginDto;
import abdumalik.dev.gymcrmv1.model.Login;
import abdumalik.dev.gymcrmv1.model.Result;
import abdumalik.dev.gymcrmv1.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoginService {

    @Autowired
    LoginRepo repo;

    public List<Login> findAll() {
        return repo.findAll();
    }

    public Login findById(UUID id) {
        return repo.findById(id).get();
    }

    public Result create(LoginDto dto) {
        boolean b = repo.existsByEmail(dto.getEmail());
        if (b) {
            return new Result("Email is already exist", false);
        }
        boolean b1 = repo.existsByUsername(dto.getUsername());
        if (b1) {
            return new Result("Username is already taken", false);
        }

        Login login = new Login();
        login.setEmail(dto.getEmail());
        login.setUsername(dto.getUsername());
        login.setPassword(dto.getPassword());
        login.setConfirmPassword(dto.getConfirmPassword());
        repo.save(login);
        return new Result("Login successfully created", true);
    }

    public Result update(UUID id, LoginDto dto) {
        Optional<Login> byId = repo.findById(id);
        if (byId.isPresent()) {
            Login login = byId.get();
            login.setEmail(dto.getEmail());
            login.setUsername(dto.getUsername());
            login.setPassword(dto.getPassword());
            login.setConfirmPassword(dto.getConfirmPassword());
            repo.save(login);
            return new Result("Login successfully updated", true);
        }
        return new Result("Login not found", false);
    }

    public Result delete(UUID id) {
        Optional<Login> byId = repo.findById(id);
        if (byId.isPresent()) {
            repo.delete(byId.get());
            return new Result("Login successfully deleted", true);
        }
        return new Result("Login not found", false);
    }

}