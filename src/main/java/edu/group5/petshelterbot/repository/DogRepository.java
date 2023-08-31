package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * {@link #setOwnerId(Long, Long)} - Добавление ID владельца питомца.
 * {@link #deleteOwnerId(Long)} - Удаление владельца у питомца.
 *  {@link #getTrialDate(Long)} - Получение даты окончания испытательного срока хозяина питомца.
 * {@link #setOwnerTrialDate(Date, Long)} - Установление тспытательного срока (30 дней с момента введения нового владельца).
 * {@link #deleteOwnerTrialDate(Long)} - Установить испытательный  срок на null.
 * {@link #setOwnerTrialDate(Date, Long)} - Установление даты испытательного срока для владельца.
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Dog findDogsById(long id);
    @Query(value = "SELECT dog_shelter.trial_date_time FROM dog_shelter WHERE id = ?", nativeQuery = true)
    Date getTrialDate(Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET owner_id = ? WHERE id = ?", nativeQuery = true)
    int setOwnerId(Long owner_id, Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET owner_id = null WHERE id = ?", nativeQuery = true)
    Integer deleteOwnerId(Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET trial_date_time = ? WHERE id = ?", nativeQuery = true)
    int setOwnerTrialDate(Date trial_date_time, Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET trial_date_time = null WHERE id = ?", nativeQuery = true)
    Integer deleteOwnerTrialDate(Long id);

}
