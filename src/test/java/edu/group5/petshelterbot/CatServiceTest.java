package edu.group5.petshelterbot;
import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.repository.CatRepository;
import edu.group5.petshelterbot.repository.DogRepository;
import edu.group5.petshelterbot.service.CatService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
@ExtendWith(MockitoExtension.class)
public class CatServiceTest {
    private static final Long CAT_ID = 1L;
    private static final String CAT_NAME = "DOG";
    private static final Integer CAT_AGE = 2;
    private static final String CAT_BREED = "BREED";
    private static final String CAT_SEX = "SEX";
    private static final Boolean CAT_IS_STERILISED = true;

    private static final Cat cat = new Cat(CAT_NAME, CAT_AGE, CAT_BREED, CAT_SEX, CAT_IS_STERILISED);

    private static final List<Cat> cats = new ArrayList<>(Arrays.asList(
            new Cat(CAT_NAME, CAT_AGE, CAT_BREED, CAT_SEX, CAT_IS_STERILISED),
            new Cat(CAT_NAME, CAT_AGE, CAT_BREED, CAT_SEX, CAT_IS_STERILISED),
            new Cat(CAT_NAME, CAT_AGE, CAT_BREED, CAT_SEX, CAT_IS_STERILISED),
            new Cat(CAT_NAME, CAT_AGE, CAT_BREED, CAT_SEX, CAT_IS_STERILISED)
    ));

    private static final Date date = new Date();

    @Mock
    private CatRepository catRepositoryMock;

    @InjectMocks
    private CatService catService;

    @Test
    void saveCat() {
        Mockito.when(catRepositoryMock.save(any(Cat.class))).thenReturn(cat);

        Cat cat1 = catService.saveCat(cat);

        Assertions.assertThat(cat1.getName()).isEqualTo(cat.getName());
        Assertions.assertThat(cat1.getAge()).isEqualTo(cat.getAge());
        Assertions.assertThat(cat1.getBreed()).isEqualTo(cat.getBreed());
        Assertions.assertThat(cat1.getSex()).isEqualTo(cat.getSex());
        Assertions.assertThat(cat1.is_sterilized()).isEqualTo(cat.is_sterilized());
    }

    @Test
    void getCatByID() {
        Mockito.when(catRepositoryMock.findCatsById(any(Long.class))).thenReturn(cat);

        Cat cat1 = catRepositoryMock.findCatsById(CAT_ID);

        Assertions.assertThat(cat1.getName()).isEqualTo(cat.getName());
        Assertions.assertThat(cat1.getAge()).isEqualTo(cat.getAge());
        Assertions.assertThat(cat1.getBreed()).isEqualTo(cat.getBreed());
        Assertions.assertThat(cat1.getSex()).isEqualTo(cat.getSex());
        Assertions.assertThat(cat1.is_sterilized()).isEqualTo(cat.is_sterilized());
    }

    @Test
    void getAllCats() {
        Mockito.when(catRepositoryMock.findAll()).thenReturn(cats);

        List<Cat> cats1 = catService.getAllCats();

        Assertions.assertThat(cats1.size()).isEqualTo(cats.size());
        Assertions.assertThat(cats1).isEqualTo(cats);
    }

    @Test
    void updateCatThrowsExceptionIfCatDoesNotExist() {
        Mockito.when(catRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        Assertions.assertThatThrownBy(
                        () -> catService.updateCat(cat)
                )
                .isInstanceOf(Exception.class)
                .hasMessage("Этот кот не найден в базе данных.");
    }

    @Test
    void updateCatIfCatDoesExist() {
        Mockito.when(catRepositoryMock.existsById(any(Long.class))).thenReturn(true);
        Mockito.when(catRepositoryMock.save(any(Cat.class))).thenReturn(cat);

        try {
            Cat cat1 = catService.updateCat(cat);

            Assertions.assertThat(cat1.getName()).isEqualTo(cat.getName());
            Assertions.assertThat(cat1.getAge()).isEqualTo(cat.getAge());
            Assertions.assertThat(cat1.getBreed()).isEqualTo(cat.getBreed());
            Assertions.assertThat(cat1.getSex()).isEqualTo(cat.getSex());
            Assertions.assertThat(cat1.is_sterilized()).isEqualTo(cat.is_sterilized());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void setCatOwner() {
        Mockito.when(catRepositoryMock.setOwnerId(any(Long.class), any(Long.class))).thenReturn(1);
        Mockito.when(catRepositoryMock.setOwnerTrialDate(any(Date.class), any(Long.class))).thenReturn(1);

        int result1 = catRepositoryMock.setOwnerId(1L, 1L);
        int result2 = catRepositoryMock.setOwnerTrialDate(new Date(), 1L);

        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(1);
    }

    @Test
    void addDaysToTrialDate() {
        Mockito.when(catRepositoryMock.setOwnerTrialDate(any(Date.class), any(Long.class))).thenReturn(1);

        int result = catRepositoryMock.setOwnerTrialDate(new Date(), 1L);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void getTrialDate() {
        Mockito.when(catRepositoryMock.getTrialDate(any(Long.class))).thenReturn(date);

        Date date1 = catService.getTrialDate(CAT_ID);

        Assertions.assertThat(date1).isEqualTo(date);
    }
}
