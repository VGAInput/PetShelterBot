package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.repository.OwnerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Сервис для (потенциальных) владельцев
 */
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public Owner getOwnerByID(long id) {
        return ownerRepository.findOwnersById(id);
    }

    public List<Owner> getAllOwners() {
        return ownerRepository.findAll();
    }

    public Owner updateOwner(Owner owner) throws Exception {
        if (ownerRepository.existsById(owner.getId())) {
            Owner updatedOwner = ownerRepository.save(owner);
            return updatedOwner;
        } else {
            throw new Exception("Этот владелец не найден в базе данных.");
        }
    }


    public void deleteOwner(Owner deleteOwner) {
        ownerRepository.delete(deleteOwner);
    }

}
