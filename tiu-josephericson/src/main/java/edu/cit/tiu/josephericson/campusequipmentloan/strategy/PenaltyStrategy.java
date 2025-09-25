package edu.cit.tiu.josephericson.campusequipmentloan.strategy;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Loan;

public interface PenaltyStrategy {
    double calculatePenalty(Loan loan);
}