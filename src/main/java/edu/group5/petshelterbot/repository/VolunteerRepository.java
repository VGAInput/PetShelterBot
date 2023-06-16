package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    List<Volunteer> findVolunteersByShelterTableName(String shelterName);
    Volunteer findVolunteersById(long id);
    boolean existsByShelterTableNameAndTgUserId(String shelterTableName,long tgUserId);
    @Query(value = "SELECT v.tg_user_id FROM volunteers v WHERE v.shelter_table_name = ?1",nativeQuery = true)
    List<Long> getVolunteersTelegramId(String shelterName);

}
