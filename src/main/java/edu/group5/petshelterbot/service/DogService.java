package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.repository.DogRepository;
import org.springframework.stereotype.Service;

@Service
public class DogService {
    private final DogRepository dogRepository;

    public DogService(DogRepository dogRepository) {
        this.dogRepository = dogRepository;
    }

    public void saveDog(Dog dog) {
        //Добавить новую собаку в базу данных.
        dogRepository.save(dog);
    }

    public void findDogById(long id) {
        //Найти собаку по ID
        dogRepository.findById(id);
    }


}
