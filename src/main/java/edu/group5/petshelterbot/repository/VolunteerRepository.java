package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    //Получить список волонтёров по названию приюта.
    List<Volunteer> findVolunteersByShelterTableName(String shelterName);
}
