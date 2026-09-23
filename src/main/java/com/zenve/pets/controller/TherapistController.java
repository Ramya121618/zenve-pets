package com.zenve.pets.controller;

import com.zenve.pets.entity.Therapist;
import com.zenve.pets.service.TherapistService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/therapists")
public class TherapistController {

    private final TherapistService therapistService;

    public TherapistController(TherapistService therapistService) {
        this.therapistService = therapistService;
    }

    @PostMapping
    public Therapist createTherapist(@Valid @RequestBody Therapist therapist) {
        return therapistService.createTherapist(therapist);
    }

    @GetMapping
    public List<Therapist> getAllTherapists() {
        return therapistService.getAllTherapists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Therapist> getTherapistById(@PathVariable Long id) {
        return therapistService.getTherapistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<Therapist> updateTherapist(
            @PathVariable Long id,
            @Valid @RequestBody Therapist therapist) {

        Therapist updatedTherapist =
                therapistService.updateTherapist(id, therapist);

        if (updatedTherapist == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTherapist);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTherapist(@PathVariable Long id) {

        boolean deleted = therapistService.deleteTherapist(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
