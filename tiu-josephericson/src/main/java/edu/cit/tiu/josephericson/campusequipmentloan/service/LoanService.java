package edu.cit.tiu.josephericson.campusequipmentloan.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;
import edu.cit.tiu.josephericson.campusequipmentloan.repository.LoanRepository;
import edu.cit.tiu.josephericson.campusequipmentloan.strategy.*;
import edu.cit.tiu.josephericson.campusequipmentloan.model.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.stream.Collectors;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    private final LoanRepository loanRepository;
    private final PenaltyStrategy penaltyStrategy;


    public LoanService(LoanRepository loanRepository, PenaltyStrategy penaltyStrategy) {
        this.loanRepository = loanRepository;
        this.penaltyStrategy = penaltyStrategy;
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
        long activeLoans = loanRepository.countByStudentIdAndStatus(loan.getStudent().getId(), "ONGOING");


        if (activeLoans >= 2) {
            throw new IllegalStateException("Student already has 2 active loans.");
        }

        if (loan.getStartDate() == null) {
            loan.setStartDate(LocalDate.now());
        }

        // Always enforce 7-day loan length
        loan.setDueDate(loan.getStartDate().plusDays(7));

        // Default status to ONGOING if not set
        if (loan.getStatus() == null) {
            loan.setStatus("ONGOING");
        }

        return loanRepository.save(loan);
    }

    public Loan checkAndUpdateOverdue(Loan loan) {
        if ("ONGOING".equals(loan.getStatus()) && LocalDate.now().isAfter(loan.getDueDate())) {
            loan.setStatus("OVERDUE");
            loanRepository.save(loan);
        }
        return loan;
    }

    public List<Loan> getAllLoans() {
        List<Loan> loans = loanRepository.findAll();
        loans.forEach(this::checkAndUpdateOverdue);
        return loans;
    }

    public double calculatePenalty(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Loan not found"));
        return penaltyStrategy.calculatePenalty(loan);
    }

}