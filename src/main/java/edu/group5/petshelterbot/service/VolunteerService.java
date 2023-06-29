package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VolunteerService {
    private final VolunteerRepository volunteerRepository;
    private Set<Volunteer> volunteers = new HashSet<>();

    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    public Volunteer saveVolunteer(Volunteer volunteer) {
        return volunteerRepository.save(volunteer);
    }

    public Volunteer getVolunteerByID(long id) {
        return volunteerRepository.findVolunteersById(id);
    }

    public List<Volunteer> getAllVolunteers() {
        return volunteerRepository.findAll();
    }

    public boolean checkVolunteerExists(String shelterName, long tgid) {
        return volunteerRepository.existsByShelterTableNameAndTgUserId(shelterName, tgid);
    }

    public List<Volunteer> getAllVolunteersFromShelter(String shelterName) {
        return volunteerRepository.findVolunteersByShelterTableName(shelterName);
    }

    //Получить список всех волонтёров из конкретного приюта.
    public List<Long> volunteerIds(String shelterName) {
        return volunteerRepository.getVolunteersFromShelter(shelterName);
    }

    //Выбрать случайного волонтёра из списка.
    public long getRandomVolunteer(String shelterName) {
        Random random = new Random();
        return volunteerIds(shelterName).get(random.nextInt(volunteerIds(shelterName).size()));
    }

    public Volunteer updateVolunteer(Volunteer volunteer) throws Exception {
        if (volunteerRepository.existsById(volunteer.getId())) {
            Volunteer updatedVolunteer = volunteerRepository.save(volunteer);
            return updatedVolunteer;
        } else {
            throw new Exception("Этот волонтёр не найден в базе данных.");
        }
    }

    public void deleteVolunteer(Volunteer deleteVolunteer) {
        volunteerRepository.delete(deleteVolunteer);
    }

}
