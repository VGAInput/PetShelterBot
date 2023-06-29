package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.repository.CatRepository;
import liquibase.pro.packaged.C;
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

    public Cat updateCat(Cat cat) throws Exception {
        if (catRepository.existsById(cat.getId())) {
            Cat updatedCat = catRepository.save(cat);
            return updatedCat;
        } else {
            throw new Exception("Этот кот не найден в базе данных.");
        }
    }

    public void deleteCat(Cat deleteCat) {
        catRepository.delete(deleteCat);
    }

    public void setCatOwner(long ownerId, long catId) {
        catRepository.setOwnerId(ownerId, catId);
    }
    public void deleteOwnerId(long catId) {
        catRepository.deleteOwnerId(catId);
    }


}
