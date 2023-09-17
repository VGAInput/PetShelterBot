package edu.group5.petshelterbot;
import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.repository.DogRepository;
import edu.group5.petshelterbot.service.DogService;
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
public class DogServiceTest {
    private static final Long DOG_ID = 1L;
    private static final String DOG_NAME = "DOG";
    private static final Integer DOG_AGE = 2;
    private static final String DOG_BREED = "BREED";
    private static final String DOG_SEX = "SEX";
    private static final Boolean DOG_IS_STERILISED = true;

    private static final Dog dog = new Dog(DOG_NAME, DOG_AGE, DOG_BREED, DOG_SEX, DOG_IS_STERILISED);

    private static final List<Dog> dogs = new ArrayList<>(Arrays.asList(
            new Dog(DOG_NAME, DOG_AGE, DOG_BREED, DOG_SEX, DOG_IS_STERILISED),
            new Dog(DOG_NAME, DOG_AGE, DOG_BREED, DOG_SEX, DOG_IS_STERILISED),
            new Dog(DOG_NAME, DOG_AGE, DOG_BREED, DOG_SEX, DOG_IS_STERILISED),
            new Dog(DOG_NAME, DOG_AGE, DOG_BREED, DOG_SEX, DOG_IS_STERILISED)
    ));

    private static final Date date = new Date();

    @Mock
    private DogRepository dogRepositoryMock;

    @InjectMocks
    private DogService dogService;

    @Test
    void saveDog() {
        Mockito.when(dogRepositoryMock.save(any(Dog.class))).thenReturn(dog);

        Dog dog1 = dogService.saveDog(dog);

        Assertions.assertThat(dog1.getName()).isEqualTo(dog.getName());
        Assertions.assertThat(dog1.getAge()).isEqualTo(dog.getAge());
        Assertions.assertThat(dog1.getBreed()).isEqualTo(dog.getBreed());
        Assertions.assertThat(dog1.getSex()).isEqualTo(dog.getSex());
        Assertions.assertThat(dog1.is_sterilized()).isEqualTo(dog.is_sterilized());
    }

    @Test
    void getDogByID() {
        Mockito.when(dogRepositoryMock.findDogsById(any(Long.class))).thenReturn(dog);

        Dog dog1 = dogRepositoryMock.findDogsById(DOG_ID);

        Assertions.assertThat(dog1.getName()).isEqualTo(dog.getName());
        Assertions.assertThat(dog1.getAge()).isEqualTo(dog.getAge());
        Assertions.assertThat(dog1.getBreed()).isEqualTo(dog.getBreed());
        Assertions.assertThat(dog1.getSex()).isEqualTo(dog.getSex());
        Assertions.assertThat(dog1.is_sterilized()).isEqualTo(dog.is_sterilized());
    }

    @Test
    void getAllDogs() {
        Mockito.when(dogRepositoryMock.findAll()).thenReturn(dogs);

        List<Dog> dogs1 = dogService.getAllDogs();

        Assertions.assertThat(dogs1.size()).isEqualTo(dogs.size());
        Assertions.assertThat(dogs1).isEqualTo(dogs);
    }

    @Test
    void updateDogThrowsExceptionIfDogDoesNotExist() {
        Mockito.when(dogRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        Assertions.assertThatThrownBy(
                        () -> dogService.updateDog(dog)
                )
                .isInstanceOf(Exception.class)
                .hasMessage("Этот пёс не найден в базе данных.");
    }

    @Test
    void updateDogIfDogDoesExist() {
        Mockito.when(dogRepositoryMock.existsById(any(Long.class))).thenReturn(true);
        Mockito.when(dogRepositoryMock.save(any(Dog.class))).thenReturn(dog);

        try {
            Dog dog1 = dogService.updateDog(dog);

            Assertions.assertThat(dog1.getName()).isEqualTo(dog.getName());
            Assertions.assertThat(dog1.getAge()).isEqualTo(dog.getAge());
            Assertions.assertThat(dog1.getBreed()).isEqualTo(dog.getBreed());
            Assertions.assertThat(dog1.getSex()).isEqualTo(dog.getSex());
            Assertions.assertThat(dog1.is_sterilized()).isEqualTo(dog.is_sterilized());
        } catch (Exception e) {
            fail();
        }

    }

    @Test
    void setDogOwner() {
        Mockito.when(dogRepositoryMock.setOwnerId(any(Long.class), any(Long.class))).thenReturn(1);
        Mockito.when(dogRepositoryMock.setOwnerTrialDate(any(Date.class), any(Long.class))).thenReturn(1);

        int result1 = dogRepositoryMock.setOwnerId(1L, 1L);
        int result2 = dogRepositoryMock.setOwnerTrialDate(new Date(), 1L);

        Assertions.assertThat(result1).isEqualTo(1);
        Assertions.assertThat(result2).isEqualTo(1);
    }

    @Test
    void addDaysToTrialDate() {
        Mockito.when(dogRepositoryMock.setOwnerTrialDate(any(Date.class), any(Long.class))).thenReturn(1);

        int result = dogRepositoryMock.setOwnerTrialDate(new Date(), 1L);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void getTrialDate() {
        Mockito.when(dogRepositoryMock.getTrialDate(any(Long.class))).thenReturn(date);

        Date date1 = dogService.getTrialDate(DOG_ID);

        Assertions.assertThat(date1).isEqualTo(date);
    }
}
