package edu.cit.tiu.josephericson.campusequipmentloan.repository;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStudentIdAndReturnDateIsNull(Long studentId);
}