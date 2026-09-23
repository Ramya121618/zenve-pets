package com.zenve.pets.repository;

import com.zenve.pets.entity.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TherapistRepository extends JpaRepository<Therapist, Long> {
}
