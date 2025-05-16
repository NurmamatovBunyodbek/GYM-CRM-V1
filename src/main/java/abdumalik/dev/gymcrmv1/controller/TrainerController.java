package abdumalik.dev.gymcrmv1.controller;

import abdumalik.dev.gymcrmv1.dto.TrainerDto;
import abdumalik.dev.gymcrmv1.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trainer")
public class TrainerController {

    @Autowired
    TrainerService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER','TRAINER')")
    public HttpEntity<?> readAll() {
        return new ResponseEntity<>(service.getAllTrainers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER','TRAINER')")
    public HttpEntity<?> readOne(@PathVariable UUID id) {
        return new ResponseEntity<>(service.getTrainerById(id), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN','USER','TRAINER')")
    public HttpEntity<?> readByUsername(@PathVariable String username) {
        return new ResponseEntity<>(service.getTrainerByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public HttpEntity<?> create(@RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(service.create(trainerDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public HttpEntity<?> update(@PathVariable UUID id, @RequestBody TrainerDto trainerDto) {
        return new ResponseEntity<>(service.update(id, trainerDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPERADMIN','ADMIN')")
    public HttpEntity<?> delete(@PathVariable UUID id) {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

}