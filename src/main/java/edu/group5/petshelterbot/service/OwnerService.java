package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.OwnerRepository;
import edu.group5.petshelterbot.repository.VolunteerRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Сервис для (потенциальных) владельцев
 */
@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private Set<Owner> owners = new HashSet<>();

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

    public Owner updateOwner(Owner updatedOwner) {
        return ownerRepository.save(updatedOwner);
    }

    public void deleteOwner(Owner deleteOwner) {
        ownerRepository.delete(deleteOwner);
    }

}
