package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public void saveVolunteer(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    public List<Volunteer> getAllVolunteersFromShelter(String shelterName) {
        return volunteerRepository.findVolunteersByShelterTableName(shelterName);
    }
}
