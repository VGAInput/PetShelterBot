package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * {@link #setOwnerId(Long, Long)} - Добавление ID владельца питомца.
 * {@link #deleteOwnerId(Long)} - Удаление владельца у питомца.
 * {@link #getTrialDate(Long)} - Получение даты окончания испытательного срока хозяина питомца.
 * {@link #setOwnerTrialDate(Date, Long)} - Установление даты испытательного срока для владельца.
 * {@link #deleteOwnerTrialDate(Long)} - Удаление даты испытательного срока для владельца.
 */
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
    Cat findCatsById(long id);

    @Query(value = "SELECT cat_shelter.trial_date_time FROM cat_shelter WHERE id = ?", nativeQuery = true)
    Date getTrialDate(Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE cat_shelter SET owner_id = ? WHERE id = ?", nativeQuery = true)
    int setOwnerId(Long owner_id, Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE cat_shelter SET owner_id = null WHERE id = ?", nativeQuery = true)
    Integer deleteOwnerId(Long id);


    @Transactional
    @Modifying()
    @Query(value = "UPDATE cat_shelter SET trial_date_time = ? WHERE id = ?", nativeQuery = true)
    int setOwnerTrialDate(Date trial_date_time, Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE cat_shelter SET trial_date_time = null WHERE id = ?", nativeQuery = true)
    Integer deleteOwnerTrialDate(Long id);

}
