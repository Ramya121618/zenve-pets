package com.zenve.pets.service;

import com.zenve.pets.entity.Therapist;
import com.zenve.pets.repository.TherapistRepository;
import org.springframework.stereotype.Service;
import com.zenve.pets.entity.Session;
import com.zenve.pets.repository.SessionRepository;
import java.util.List;


import java.util.Optional;
@Service
public class TherapistService {
    private final TherapistRepository therapistRepository;
    private final SessionRepository sessionRepository;

    public TherapistService(TherapistRepository therapistRepository,SessionRepository sessionRepository) {
        this.therapistRepository = therapistRepository;
        this.sessionRepository = sessionRepository;
    }
    public List<Session> getSessionsByTherapist(Long therapistId) {
        return sessionRepository.findByTherapistId(therapistId);
    }

    public Therapist createTherapist(Therapist therapist) {
        return therapistRepository.save(therapist);
    }

    public List<Therapist> getAllTherapists() {
        return therapistRepository.findAll();
    }

    public Optional<Therapist> getTherapistById(Long id) {
        return therapistRepository.findById(id);
    }

    public Therapist updateTherapist(Long id, Therapist therapist){

        return therapistRepository.findById(id)
                .map(existingTherapist -> {
                    existingTherapist.setName(therapist.getName());
                    existingTherapist.setSpecialization(therapist.getSpecialization());
                    existingTherapist.setSessionType(therapist.getSessionType());

                    return therapistRepository.save(existingTherapist);
                })
                .orElse(null);
    }

    public boolean deleteTherapist(Long id) {
        if (therapistRepository.existsById(id)) {
            therapistRepository.deleteById(id);
            return true;
        }

        return false;
    }
}
