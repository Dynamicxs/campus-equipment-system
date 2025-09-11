package edu.cit.tiu.josephericson.campusequipmentloan.repository;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email); // For login or search

    List<Student> findByNameContainingIgnoreCase(String name); // For search suggestions

    boolean existsByEmail(String email);
}
