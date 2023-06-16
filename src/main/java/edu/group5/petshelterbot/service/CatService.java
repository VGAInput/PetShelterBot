package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.repository.CatRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatService {
    private final CatRepository catRepository;
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
    public Cat saveCat(Cat cat) {
       return catRepository.save(cat);
    }
    public Cat getCatByID(long id) {
       return catRepository.findCatsById(id);
    }
    public List<Cat> getAllCats() {
        return catRepository.findAll();
    }

    public Cat updateCat(Cat updatedCat) {
        return catRepository.save(updatedCat);
    }

    public void deleteCat(Cat deleteCat) {
        catRepository.delete(deleteCat);
    }


}
