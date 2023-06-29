package edu.group5.petshelterbot.repository;

import edu.group5.petshelterbot.entity.Dog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link #setOwnerId(Long, Long)} - Добавление ID владельца питомца.
 * {@link #deleteOwnerId(Long)} - Удаление владельца у питомца.
 */
@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Dog findDogsById(long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET owner_id = ? WHERE id = ?", nativeQuery = true)
    int setOwnerId(Long owner_id, Long id);

    @Transactional
    @Modifying()
    @Query(value = "UPDATE dog_shelter SET owner_id = null WHERE id = ?", nativeQuery = true)
    Integer deleteOwnerId(Long id);

}
