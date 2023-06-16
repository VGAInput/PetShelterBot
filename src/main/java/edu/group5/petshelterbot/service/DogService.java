package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.DogRepository;
import liquibase.pro.packaged.D;
import org.springframework.stereotype.Service;

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

    public Dog updateDog(Dog updatedDog) {
        return dogRepository.save(updatedDog);
    }

    public void deleteDog(Dog deleteDog) {
        dogRepository.delete(deleteDog);
    }


}
