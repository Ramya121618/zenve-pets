package com.zenve.pets.service;

import com.zenve.pets.entity.Pet;
import com.zenve.pets.repository.PetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository){
        this.petRepository = petRepository;
    }
    //creating a new pet
    public Pet createPet(Pet pet){
        return petRepository.save(pet);
    }
    //get all pets
    public List<Pet> getAllPets(){
        return petRepository.findAll();
    }
    //get pet by id
    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public Pet updatePet(Long id, Pet pet) {
        return petRepository.findById(id)
                .map(existingPet -> {
                    existingPet.setName(pet.getName());
                    existingPet.setAge(pet.getAge());
                    existingPet.setBreed(pet.getBreed());

                    return petRepository.save(existingPet);
                })
                .orElse(null);
    }

    public boolean deletePet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
            return true;
        }

        return false;
    }
}


