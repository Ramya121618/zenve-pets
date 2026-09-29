
package com.zenve.pets.controller;

import com.zenve.pets.entity.Owner;
import com.zenve.pets.entity.Pet;
import com.zenve.pets.service.OwnerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping("/{ownerId}/pets")
    public List<Pet> getPetsByOwner(@PathVariable Long ownerId) {
        return ownerService.getPetsByOwner(ownerId);
    }

    @GetMapping("/email/{email}")
    public Owner getOwnerByEmail(@PathVariable String email) {
        return ownerService.getOwnerByEmail(email);
    }
}

