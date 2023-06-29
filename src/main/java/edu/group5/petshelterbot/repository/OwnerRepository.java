package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Owner findOwnersById(long id);

}
