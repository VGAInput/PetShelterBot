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

    public void saveVolunteer(Volunteer volunteer) {
        volunteerRepository.save(volunteer);
    }

    public boolean checkVolunteerExists(String shelterName, long tgid) {
        return volunteerRepository.existsByShelterTableNameAndTgUserId(shelterName, tgid);
    }

    public List<Volunteer> getAllVolunteersFromShelter(String shelterName) {
        return volunteerRepository.findVolunteersByShelterTableName(shelterName);
    }

    //Получить список всех волонтёров из конкретного приюта.
    public List<Long> volunteerIds(String shelterName) {
        return volunteerRepository.getVolunteersTelegramId(shelterName);
    }

    //Выбрать случайного волонтёра из списка.
    public long getRandomVolunteer(String shelterName) {
        Random random = new Random();
        return volunteerIds(shelterName).get(random.nextInt(volunteerIds(shelterName).size()));
    }
}
