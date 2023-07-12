package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.repository.DogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public Dog saveDog(Dog dog) {
        return dogRepository.save(dog);
    }

    public Dog getDogByID(long id) {
        return dogRepository.findDogsById(id);
    }


    public List<Dog> getAllDogs() {
        return dogRepository.findAll();
    }

    public Dog updateDog(Dog dog) throws Exception {
        if (dogRepository.existsById(dog.getId())) {
            Dog updatedDog = dogRepository.save(dog);
            return updatedDog;
        } else {
            throw new Exception("Этот пёс не найден в базе данных.");
        }
    }

    public void deleteDog(Dog deleteDog) {
        dogRepository.delete(deleteDog);
    }

    public void setDogOwner(long ownerId, long dogId) {
        Calendar trialDate = Calendar.getInstance();
        trialDate.add(Calendar.DATE, 30);
        dogRepository.setOwnerTrialDate(trialDate.getTime(), dogId);
        dogRepository.setOwnerId(ownerId, dogId);
    }
    public void addDaysToTrialDate(int extraDays, long dogId) {
        Calendar trialDate = Calendar.getInstance();
        trialDate.add(Calendar.DATE, extraDays);
        dogRepository.setOwnerTrialDate(trialDate.getTime(), dogId);
    }
    public Date getTrialDate(long id){
        return dogRepository.getTrialDate(id);
    }

    public void deleteOwnerId(long dogId) {
        dogRepository.deleteOwnerTrialDate(dogId);
        dogRepository.deleteOwnerId(dogId);
    }

}
