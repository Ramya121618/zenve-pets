package com.zenve.pets.repository;

import com.zenve.pets.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByTherapistId(Long therapistId);
}
