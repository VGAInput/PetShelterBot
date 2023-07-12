package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

/**
 * Сервис для (потенциальных) владельцев
 * <p>
 * {@link #getCatsIDs(long)} (long)} - получение листа с ID Котов владельца.
 * {@link #getDogsIDs(long)} (long)} - получение листа с ID Собак владельца}
 */
@Service


public class OwnerService {

    @Value("${path.to.owner.files}")
    private String photosFolder;
    private final OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public Owner saveOwner(Owner owner) {
        if (!checkOwnerExistsByTgId(owner.getTgUserId())) {
            return ownerRepository.save(owner);
        } else return null;
    }

    public Owner getOwnerByID(long id) {
        return ownerRepository.findOwnersById(id);
    }

    public Owner getOwnerByTgUserId(long userId) {
        return ownerRepository.findOwnerByTgUserId(userId);
    }

    public void setOwnerTelephoneNumber(long id, String telephoneNumber) {
        ownerRepository.setOwnerPhoneNumber(telephoneNumber, id);
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

    public List<Integer> getCatsIDs(long owner_id) {
        return ownerRepository.getCatsOfOwner(owner_id);
    }

    public List<Integer> getDogsIDs(long owner_id) {
        return ownerRepository.getDogsOfOwner(owner_id);
    }

    public boolean checkOwnerExists(long id) {
        return ownerRepository.existsById(id);
    }

    public boolean checkOwnerExistsByTgId(long id) {
        return ownerRepository.existsByTgUserId(id);
    }

    public void deleteOwner(Owner deleteOwner) {
        ownerRepository.delete(deleteOwner);
    }

}
