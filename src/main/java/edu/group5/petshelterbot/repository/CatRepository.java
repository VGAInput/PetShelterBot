package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    Cat findCatsById(long id);

}
