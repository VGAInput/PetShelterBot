package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.repository.CatRepository;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }

    public void saveCat(Cat cat) {
        //Добавить новую кошку в базу данных.
        catRepository.save(cat);
    }

    public void findCatById(long id) {
        //Найти кошку по id.
        catRepository.findById(id);
    }


}
