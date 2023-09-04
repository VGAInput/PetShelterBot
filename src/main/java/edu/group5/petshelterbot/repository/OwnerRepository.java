package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnersById(long id);

    Owner findOwnerByTgUserId(long tgUserId);

    boolean existsByTgUserId(long tgUserId);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE owners SET telephone_number = ? WHERE id = ?", nativeQuery = true)
    int setOwnerPhoneNumber(String telephone_number, Long id);

    @Query(value = "SELECT cat_shelter.id FROM cat_shelter WHERE cat_shelter.owner_id = ?", nativeQuery = true)
    List<Integer> getCatsOfOwner(Long owner_id);

    @Query(value = "SELECT dog_shelter.id FROM dog_shelter WHERE dog_shelter.owner_id = ?", nativeQuery = true)
    List<Integer> getDogsOfOwner(Long owner_id);

}
