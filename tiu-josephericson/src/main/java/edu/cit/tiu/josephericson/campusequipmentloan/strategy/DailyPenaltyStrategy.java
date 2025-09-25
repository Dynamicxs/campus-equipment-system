package edu.cit.tiu.josephericson.campusequipmentloan.strategy;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoUnit;

@Component
public class DailyPenaltyStrategy implements PenaltyStrategy {

    private static final double PENALTY_PER_DAY = 50.0;

    @Override
    public double calculatePenalty(Loan loan) {
        if (loan == null || loan.getDueDate() == null || loan.getReturnDate() == null) {
            return 0.0;
        }
        long daysLate = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
        return daysLate > 0 ? daysLate * PENALTY_PER_DAY : 0.0;
    }
}