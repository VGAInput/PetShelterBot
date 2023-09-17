package edu.group5.petshelterbot;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.repository.OwnerRepository;
import edu.group5.petshelterbot.service.OwnerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class OwnerServiceTest {
    private static final Long ID = 1L;
    private static final Long TG_USER_ID = 1L;
    private static final String NAME = "name";
    private static final String TELEPHONE_NUMBER = "telephone number";
    private static final String CAR_NUMBER = "car number";

    private final Owner owner = new Owner(ID, TG_USER_ID, NAME, TELEPHONE_NUMBER, CAR_NUMBER);

    private final List<Owner> owners = new ArrayList<>(Arrays.asList(
            new Owner(ID, TG_USER_ID, NAME, TELEPHONE_NUMBER, CAR_NUMBER),
            new Owner(ID, TG_USER_ID, NAME, TELEPHONE_NUMBER, CAR_NUMBER),
            new Owner(ID, TG_USER_ID, NAME, TELEPHONE_NUMBER, CAR_NUMBER),
            new Owner(ID, TG_USER_ID, NAME, TELEPHONE_NUMBER, CAR_NUMBER)
    ));

    private final List<Integer> ids = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    @Mock
    private OwnerRepository ownerRepositoryMock;

    @InjectMocks
    private OwnerService ownerService;

    @Test
    void saveOwner() {
        Mockito.when(ownerRepositoryMock.save(any(Owner.class))).thenReturn(owner);

        Owner owner1 = ownerService.saveOwner(owner);

        Assertions.assertThat(owner1.getTgUserId()).isEqualTo(owner.getTgUserId());
        Assertions.assertThat(owner1.getName()).isEqualTo(owner.getName());
        Assertions.assertThat(owner1.getTelephoneNumber()).isEqualTo(owner.getTelephoneNumber());
        Assertions.assertThat(owner1.getCarNumber()).isEqualTo(owner.getCarNumber());
    }

    @Test
    void getOwnerByID() {
        Mockito.when(ownerRepositoryMock.findOwnersById(any(Long.class))).thenReturn(owner);

        Owner owner1 = ownerService.getOwnerByID(1L);

        Assertions.assertThat(owner1.getTgUserId()).isEqualTo(owner.getTgUserId());
        Assertions.assertThat(owner1.getName()).isEqualTo(owner.getName());
        Assertions.assertThat(owner1.getTelephoneNumber()).isEqualTo(owner.getTelephoneNumber());
        Assertions.assertThat(owner1.getCarNumber()).isEqualTo(owner.getCarNumber());
    }

    @Test
    void getOwnerByTgUserId() {
        Mockito.when(ownerRepositoryMock.findOwnerByTgUserId(any(Long.class))).thenReturn(owner);

        Owner owner1 = ownerService.getOwnerByTgUserId(1L);

        Assertions.assertThat(owner1.getTgUserId()).isEqualTo(owner.getTgUserId());
        Assertions.assertThat(owner1.getName()).isEqualTo(owner.getName());
        Assertions.assertThat(owner1.getTelephoneNumber()).isEqualTo(owner.getTelephoneNumber());
        Assertions.assertThat(owner1.getCarNumber()).isEqualTo(owner.getCarNumber());
    }

    @Test
    void setOwnerTelephoneNumber() {
        Mockito.when(ownerRepositoryMock.setOwnerPhoneNumber(any(String.class), any(Long.class))).thenReturn(1);

        int result = ownerRepositoryMock.setOwnerPhoneNumber("number", 1L);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void getAllOwners() {
        Mockito.when(ownerRepositoryMock.findAll()).thenReturn(owners);

        List<Owner> owners1 = ownerService.getAllOwners();

        Assertions.assertThat(owners1.size()).isEqualTo(owners.size());
        Assertions.assertThat(owners1).isEqualTo(owners);
    }

    @Test
    void updateOwnerReturnsOwnerIfOwnerIsExists() {
        Mockito.when(ownerRepositoryMock.existsById(any(Long.class))).thenReturn(true);
        Mockito.when(ownerRepositoryMock.save(any(Owner.class))).thenReturn(owner);

        try {
            Owner owner1 = ownerService.updateOwner(owner);

            Assertions.assertThat(owner1.getTgUserId()).isEqualTo(owner.getTgUserId());
            Assertions.assertThat(owner1.getName()).isEqualTo(owner.getName());
            Assertions.assertThat(owner1.getTelephoneNumber()).isEqualTo(owner.getTelephoneNumber());
            Assertions.assertThat(owner1.getCarNumber()).isEqualTo(owner.getCarNumber());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void updateOwnerThrowsExceptionIfOwnerDoesNotExist() {
        Mockito.when(ownerRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        Assertions.assertThatThrownBy(
                        () -> ownerService.updateOwner(owner)
                )
                .isInstanceOf(Exception.class)
                .hasMessage("Этот владелец не найден в базе данных.");
    }

    @Test
    void getCatsIDs() {
        Mockito.when(ownerRepositoryMock.getCatsOfOwner(any(Long.class))).thenReturn(ids);

        List<Integer> catsIds = ownerService.getCatsIDs(1L);

        Assertions.assertThat(catsIds.size()).isEqualTo(ids.size());
        Assertions.assertThat(catsIds).isEqualTo(ids);
    }

    @Test
    void getDogsIDs() {
        Mockito.when(ownerRepositoryMock.getDogsOfOwner(any(Long.class))).thenReturn(ids);

        List<Integer> dogsIds = ownerService.getDogsIDs(1L);

        Assertions.assertThat(dogsIds.size()).isEqualTo(ids.size());
        Assertions.assertThat(dogsIds).isEqualTo(ids);
    }

    @Test
    void checkOwnerExists() {
        Mockito.when(ownerRepositoryMock.existsById(any(Long.class))).thenReturn(true);

        boolean result = ownerService.checkOwnerExists(1L);

        Assertions.assertThat(result).isTrue();
    }

    @Test
    void checkOwnerExistsByTgId() {
        Mockito.when(ownerRepositoryMock.existsByTgUserId(any(Long.class))).thenReturn(true);

        boolean result = ownerService.checkOwnerExistsByTgId(1L);

        Assertions.assertThat(result).isTrue();
    }
}
