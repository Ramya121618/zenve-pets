package com.zenve.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zenve.pets.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
