package edu.cit.tiu.josephericson.campusequipmentloan.controller;

import edu.cit.tiu.josephericson.campusequipmentloan.model.Equipment;
import edu.cit.tiu.josephericson.campusequipmentloan.repository.EquipmentRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {

    private final EquipmentRepository equipmentRepository;

    public EquipmentController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping
    public List<Equipment> getAll() {
        return equipmentRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Equipment> create(@Valid @RequestBody Equipment equipment) {
        return ResponseEntity.ok(equipmentRepository.save(equipment));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Equipment> update(@PathVariable Long id, @Valid @RequestBody Equipment updated) {
        return equipmentRepository.findById(id).map(existing -> {
            existing.setName(updated.getName());
            existing.setType(updated.getType());
            existing.setSerialNumber(updated.getSerialNumber());
            existing.setAvailability(updated.isAvailability());
            return ResponseEntity.ok(equipmentRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (equipmentRepository.existsById(id)) {
            equipmentRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}