package com.zenve.pets.controller;

import com.zenve.pets.entity.Pet;
import com.zenve.pets.service.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/pets")
public class PetController {
    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    // Create a new pet
    @PostMapping
    public Pet createPet(@Valid @RequestBody Pet pet) {
        return petService.createPet(pet);
    }

    // Get all pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    // Get pet by ID
    @GetMapping("/{id}")
    public ResponseEntity<Pet> getPetById(@PathVariable Long id) {
        return petService.getPetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> updatePet(
            @PathVariable Long id,
            @Valid @RequestBody Pet pet) {

        Pet updatedPet = petService.updatePet(id, pet);

        if (updatedPet == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedPet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {

        boolean deleted = petService.deletePet(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}
