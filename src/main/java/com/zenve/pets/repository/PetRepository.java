package com.zenve.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zenve.pets.entity.Pet;
import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {
    List<Pet> findByOwnerId(Long ownerId);
}
