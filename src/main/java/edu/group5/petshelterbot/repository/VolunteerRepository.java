package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * {@link #getVolunteersFromShelter(String)  - Получения списка волонтёров}
 * из конкретного приюта - вписывается название таблицы приюта из БД.
 *
 *
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    List<Volunteer> findVolunteersByShelterTableName(String shelterName);
    Volunteer findVolunteerByTgUserId(long tgUserId);



    Volunteer findVolunteersById(long id);
    boolean existsByShelterTableNameAndTgUserId(String shelterTableName,long tgUserId);
    @Query(value = "SELECT v.tg_user_id FROM volunteers v WHERE v.shelter_table_name = ?1 AND v.is_ready = 1",nativeQuery = true)
    List<Long> getVolunteersFromShelter(String shelterName);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE volunteers SET is_ready = ? WHERE id = ?", nativeQuery = true)
    int setReady(Integer is_ready, Long id);

}
