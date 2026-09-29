package com.zenve.pets.service;

import com.zenve.pets.entity.Owner;
import com.zenve.pets.entity.Pet;
import com.zenve.pets.repository.OwnerRepository;
import com.zenve.pets.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerService {

    private final PetRepository petRepository;
    private final OwnerRepository ownerRepository;

    public OwnerService(PetRepository petRepository,
                        OwnerRepository ownerRepository) {
        this.petRepository = petRepository;
        this.ownerRepository = ownerRepository;
    }

    public List<Pet> getPetsByOwner(Long ownerId) {
        return petRepository.findByOwnerId(ownerId);
    }

    public Owner getOwnerByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }
}
