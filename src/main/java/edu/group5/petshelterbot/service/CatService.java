package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.repository.CatRepository;
import liquibase.pro.packaged.C;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
        Calendar trialDate = Calendar.getInstance();
        trialDate.add(Calendar.DATE, 30);
        catRepository.setOwnerTrialDate(trialDate.getTime(), catId);
        catRepository.setOwnerId(ownerId, catId);
    }
    public void addDaysToTrialDate(int extraDays, long dogId) {
        Calendar trialDate = Calendar.getInstance();
        trialDate.add(Calendar.DATE, extraDays);
        catRepository.setOwnerTrialDate(trialDate.getTime(), dogId);
    }

    public Date getTrialDate(long id){
        return catRepository.getTrialDate(id);
    }

    public void deleteOwnerId(long catId) {
        catRepository.deleteOwnerTrialDate(catId);
        catRepository.deleteOwnerId(catId);
    }
}
