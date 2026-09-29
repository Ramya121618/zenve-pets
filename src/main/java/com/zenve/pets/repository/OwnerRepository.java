package com.zenve.pets.repository;

import com.zenve.pets.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OwnerRepository extends JpaRepository<Owner, Long> {
      Owner findByEmail(String email);
}
