package edu.group5.petshelterbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.repository.OwnerRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {OwnerService.class})
@ExtendWith(SpringExtension.class)
class OwnerServiceTest {
    @MockBean
    private OwnerRepository ownerRepository;

    @Autowired
    private OwnerService ownerService;

    @Test
    void testSaveOwner() {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.existsByTgUserId(anyLong())).thenReturn(true);
        when(ownerRepository.save((Owner) any())).thenReturn(owner);

        Owner owner1 = new Owner();
        owner1.setCarNumber("42");
        owner1.setId(1L);
        owner1.setName("Bella");
        owner1.setTelephoneNumber("6625550144");
        owner1.setTgUserId(1L);
        assertNull(ownerService.saveOwner(owner1));
        verify(ownerRepository).existsByTgUserId(anyLong());
    }

    @Test
    void testSaveOwner2() {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.existsByTgUserId(anyLong())).thenReturn(false);
        when(ownerRepository.save((Owner) any())).thenReturn(owner);

        Owner owner1 = new Owner();
        owner1.setCarNumber("42");
        owner1.setId(1L);
        owner1.setName("Bella");
        owner1.setTelephoneNumber("6625550144");
        owner1.setTgUserId(1L);
        assertSame(owner, ownerService.saveOwner(owner1));
        verify(ownerRepository).existsByTgUserId(anyLong());
        verify(ownerRepository).save((Owner) any());
    }

    @Test
    void testGetOwnerByID() {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.findOwnersById(anyLong())).thenReturn(owner);
        assertSame(owner, ownerService.getOwnerByID(1L));
        verify(ownerRepository).findOwnersById(anyLong());
    }

    @Test
    void testGetOwnerByTgUserId() {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.findOwnerByTgUserId(anyLong())).thenReturn(owner);
        assertSame(owner, ownerService.getOwnerByTgUserId(1L));
        verify(ownerRepository).findOwnerByTgUserId(anyLong());
    }

    @Test
    void testSetOwnerTelephoneNumber() {
        when(ownerRepository.setOwnerPhoneNumber((String) any(), (Long) any())).thenReturn(10);
        ownerService.setOwnerTelephoneNumber(1L, "6625550144");
        verify(ownerRepository).setOwnerPhoneNumber((String) any(), (Long) any());
    }

    @Test
    void testGetAllOwners() {
        ArrayList<Owner> ownerList = new ArrayList<>();
        when(ownerRepository.findAll()).thenReturn(ownerList);
        List<Owner> actualAllOwners = ownerService.getAllOwners();
        assertSame(ownerList, actualAllOwners);
        assertTrue(actualAllOwners.isEmpty());
        verify(ownerRepository).findAll();
    }

    @Test
    void testUpdateOwner() throws Exception {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.save((Owner) any())).thenReturn(owner);
        when(ownerRepository.existsById((Long) any())).thenReturn(true);

        Owner owner1 = new Owner();
        owner1.setCarNumber("42");
        owner1.setId(1L);
        owner1.setName("Bella");
        owner1.setTelephoneNumber("6625550144");
        owner1.setTgUserId(1L);
        assertSame(owner, ownerService.updateOwner(owner1));
        verify(ownerRepository).existsById((Long) any());
        verify(ownerRepository).save((Owner) any());
    }

    @Test
    void testUpdateOwner2() throws Exception {
        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        when(ownerRepository.save((Owner) any())).thenReturn(owner);
        when(ownerRepository.existsById((Long) any())).thenReturn(false);

        Owner owner1 = new Owner();
        owner1.setCarNumber("42");
        owner1.setId(1L);
        owner1.setName("Bella");
        owner1.setTelephoneNumber("6625550144");
        owner1.setTgUserId(1L);
        assertThrows(Exception.class, () -> ownerService.updateOwner(owner1));
        verify(ownerRepository).existsById((Long) any());
    }

    @Test
    void testGetCatsIDs() {
        ArrayList<Integer> integerList = new ArrayList<>();
        when(ownerRepository.getCatsOfOwner((Long) any())).thenReturn(integerList);
        List<Integer> actualCatsIDs = ownerService.getCatsIDs(1L);
        assertSame(integerList, actualCatsIDs);
        assertTrue(actualCatsIDs.isEmpty());
        verify(ownerRepository).getCatsOfOwner((Long) any());
    }

    @Test
    void testGetDogsIDs() {
        ArrayList<Integer> integerList = new ArrayList<>();
        when(ownerRepository.getDogsOfOwner((Long) any())).thenReturn(integerList);
        List<Integer> actualDogsIDs = ownerService.getDogsIDs(1L);
        assertSame(integerList, actualDogsIDs);
        assertTrue(actualDogsIDs.isEmpty());
        verify(ownerRepository).getDogsOfOwner((Long) any());
    }

    @Test
    void testCheckOwnerExists() {
        when(ownerRepository.existsById((Long) any())).thenReturn(true);
        assertTrue(ownerService.checkOwnerExists(1L));
        verify(ownerRepository).existsById((Long) any());
    }

    @Test
    void testCheckOwnerExists2() {
        when(ownerRepository.existsById((Long) any())).thenReturn(false);
        assertFalse(ownerService.checkOwnerExists(1L));
        verify(ownerRepository).existsById((Long) any());
    }

    @Test
    void testCheckOwnerExistsByTgId() {
        when(ownerRepository.existsByTgUserId(anyLong())).thenReturn(true);
        assertTrue(ownerService.checkOwnerExistsByTgId(1L));
        verify(ownerRepository).existsByTgUserId(anyLong());
    }

    @Test
    void testCheckOwnerExistsByTgId2() {
        when(ownerRepository.existsByTgUserId(anyLong())).thenReturn(false);
        assertFalse(ownerService.checkOwnerExistsByTgId(1L));
        verify(ownerRepository).existsByTgUserId(anyLong());
    }

    @Test
    void testDeleteOwner() {
        doNothing().when(ownerRepository).delete((Owner) any());

        Owner owner = new Owner();
        owner.setCarNumber("42");
        owner.setId(1L);
        owner.setName("Bella");
        owner.setTelephoneNumber("6625550144");
        owner.setTgUserId(1L);
        ownerService.deleteOwner(owner);
        verify(ownerRepository).delete((Owner) any());
        assertEquals("42", owner.getCarNumber());
        assertEquals(1L, owner.getTgUserId());
        assertEquals("6625550144", owner.getTelephoneNumber());
        assertEquals("Bella", owner.getName());
        assertEquals(1L, owner.getId());
    }
}

