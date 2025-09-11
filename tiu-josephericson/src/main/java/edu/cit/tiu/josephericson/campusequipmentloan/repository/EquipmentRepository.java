package edu.cit.tiu.josephericson.campusequipmentloan.repository;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByStatus(String status);
}