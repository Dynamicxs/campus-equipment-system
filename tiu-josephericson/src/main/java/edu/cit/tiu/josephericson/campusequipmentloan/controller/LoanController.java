package edu.cit.tiu.josephericson.campusequipmentloan.controller;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;
import edu.cit.tiu.josephericson.campusequipmentloan.repository.LoanRepository;
import edu.cit.tiu.josephericson.campusequipmentloan.service.LoanService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Loan")
public class LoanController {

    private final LoanRepository loanRepository;
    private final LoanService loanService;

    public LoanController(LoanRepository loanRepository, LoanService loanService) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
    }

    @GetMapping
    public List<Loan> getAll() {
        return loanRepository.findAll();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Loan> update(@PathVariable Long id, @Valid @RequestBody Loan updated) {
        return loanRepository.findById(id).map(existing -> {
            existing.setEquipment(updated.getEquipment());
            existing.setStudent(updated.getStudent());
            existing.setStartDate(updated.getStartDate());
            existing.setDueDate(updated.getDueDate());
            existing.setReturnDate(updated.getReturnDate());
            existing.setStatus(updated.getStatus());
            return ResponseEntity.ok(loanRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (loanRepository.existsById(id)) {
            loanRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Additional methods (getById, update, delete) can be added similarly
}
