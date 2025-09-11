package edu.cit.tiu.josephericson.campusequipmentloan.service;

import org.springframework.stereotype.Service;
import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;
import edu.cit.tiu.josephericson.campusequipmentloan.repository.LoanRepository;
import edu.cit.tiu.josephericson.campusequipmentloan.model.*;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;

    public LoanService(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public Optional<Loan> findById(Long id) {
        return loanRepository.findById(id);
    }

    public Loan save(Loan loan) {
        return loanRepository.save(loan);
    }

    public void deleteById(Long id) {
        loanRepository.deleteById(id);
    }

    public Loan createLoan(Loan loan) {
        // Set loan duration automatically
        loan.setDueDate(loan.getStartDate().plusDays(7)); // Rule 2
        loan.setStatus("ACTIVE");

        return loanRepository.save(loan);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }
    public List<Equipment> getActiveEquipmentsByStudent(Long studentId) {
        List<Loan> activeLoans = loanRepository.findByStudentIdAndReturnDateIsNull(studentId);

        return activeLoans.stream()
                .map(Loan::getEquipment)
                .collect(Collectors.toList());
    }
}