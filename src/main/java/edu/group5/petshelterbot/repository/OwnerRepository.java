package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnersById(long id);

}
