package edu.group5.petshelterbot.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.VolunteerRepository;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {VolunteerService.class})
@ExtendWith(SpringExtension.class)
class VolunteerServiceTest {
    @MockBean
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerService volunteerService;

    @Test
    void testSaveVolunteer() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        when(volunteerRepository.save((Volunteer) any())).thenReturn(volunteer);

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setIsReady(1);
        volunteer1.setName("Bella");
        volunteer1.setShelterTableName("Bella");
        volunteer1.setTgUserId(1L);
        assertSame(volunteer, volunteerService.saveVolunteer(volunteer1));
        verify(volunteerRepository).save((Volunteer) any());
    }

    @Test
    void testGetVolunteerByID() {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        when(volunteerRepository.findVolunteersById(anyLong())).thenReturn(volunteer);
        assertSame(volunteer, volunteerService.getVolunteerByID(1L));
        verify(volunteerRepository).findVolunteersById(anyLong());
    }

    @Test
    void testGetAllVolunteers() {
        ArrayList<Volunteer> volunteerList = new ArrayList<>();
        when(volunteerRepository.findAll()).thenReturn(volunteerList);
        List<Volunteer> actualAllVolunteers = volunteerService.getAllVolunteers();
        assertSame(volunteerList, actualAllVolunteers);
        assertTrue(actualAllVolunteers.isEmpty());
        verify(volunteerRepository).findAll();
    }

    @Test
    void testCheckVolunteerExists() {
        when(volunteerRepository.existsByShelterTableNameAndTgUserId((String) any(), anyLong())).thenReturn(true);
        assertTrue(volunteerService.checkVolunteerExists("Bella", 1L));
        verify(volunteerRepository).existsByShelterTableNameAndTgUserId((String) any(), anyLong());
    }

    @Test
    void testCheckVolunteerExists2() {
        when(volunteerRepository.existsByShelterTableNameAndTgUserId((String) any(), anyLong())).thenReturn(false);
        assertFalse(volunteerService.checkVolunteerExists("Bella", 1L));
        verify(volunteerRepository).existsByShelterTableNameAndTgUserId((String) any(), anyLong());
    }

    @Test
    void testGetAllVolunteersFromShelter() {
        ArrayList<Volunteer> volunteerList = new ArrayList<>();
        when(volunteerRepository.findVolunteersByShelterTableName((String) any())).thenReturn(volunteerList);
        List<Volunteer> actualAllVolunteersFromShelter = volunteerService.getAllVolunteersFromShelter("Bella");
        assertSame(volunteerList, actualAllVolunteersFromShelter);
        assertTrue(actualAllVolunteersFromShelter.isEmpty());
        verify(volunteerRepository).findVolunteersByShelterTableName((String) any());
    }

    @Test
    void testVolunteerIds() {
        ArrayList<Long> resultLongList = new ArrayList<>();
        when(volunteerRepository.getVolunteersFromShelter((String) any())).thenReturn(resultLongList);
        List<Long> actualVolunteerIdsResult = volunteerService.volunteerIds("Bella");
        assertSame(resultLongList, actualVolunteerIdsResult);
        assertTrue(actualVolunteerIdsResult.isEmpty());
        verify(volunteerRepository).getVolunteersFromShelter((String) any());
    }

    @Test
    @Disabled()
    void testGetRandomVolunteer() {
        when(volunteerRepository.getVolunteersFromShelter((String) any())).thenReturn(new ArrayList<>());
        volunteerService.getRandomVolunteer("Bella");
    }

    @Test
    void testGetRandomVolunteer2() {
        ArrayList<Long> resultLongList = new ArrayList<>();
        resultLongList.add(1L);
        when(volunteerRepository.getVolunteersFromShelter((String) any())).thenReturn(resultLongList);
        assertEquals(1L, volunteerService.getRandomVolunteer("Bella"));
        verify(volunteerRepository, atLeast(1)).getVolunteersFromShelter((String) any());
    }

    @Test
    void testIsCurrentUserVolunteer() {
        when(volunteerRepository.findVolunteerByTgUserId(anyLong())).thenReturn(new ArrayList<>());
        assertTrue(volunteerService.isCurrentUserVolunteer(1L));
        verify(volunteerRepository).findVolunteerByTgUserId(anyLong());
    }

    @Test
    void testIsReady() {
        when(volunteerRepository.setReady((Integer) any(), (Long) any())).thenReturn(1);
        volunteerService.isReady(true, 1L);
        verify(volunteerRepository).setReady((Integer) any(), (Long) any());
    }

    @Test
    void testIsReady2() {
        when(volunteerRepository.setReady((Integer) any(), (Long) any())).thenReturn(1);
        volunteerService.isReady(false, 1L);
        verify(volunteerRepository).setReady((Integer) any(), (Long) any());
    }

    @Test
    void testGetVolunteerByTgUserId() {
        ArrayList<Volunteer> volunteerList = new ArrayList<>();
        when(volunteerRepository.findVolunteerByTgUserId(anyLong())).thenReturn(volunteerList);
        List<Volunteer> actualVolunteerByTgUserId = volunteerService.getVolunteerByTgUserId(1L);
        assertSame(volunteerList, actualVolunteerByTgUserId);
        assertTrue(actualVolunteerByTgUserId.isEmpty());
        verify(volunteerRepository).findVolunteerByTgUserId(anyLong());
    }

    @Test
    void testUpdateVolunteer() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        when(volunteerRepository.save((Volunteer) any())).thenReturn(volunteer);
        when(volunteerRepository.existsById((Long) any())).thenReturn(true);

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setIsReady(1);
        volunteer1.setName("Bella");
        volunteer1.setShelterTableName("Bella");
        volunteer1.setTgUserId(1L);
        assertSame(volunteer, volunteerService.updateVolunteer(volunteer1));
        verify(volunteerRepository).existsById((Long) any());
        verify(volunteerRepository).save((Volunteer) any());
    }

    @Test
    void testUpdateVolunteer2() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        when(volunteerRepository.save((Volunteer) any())).thenReturn(volunteer);
        when(volunteerRepository.existsById((Long) any())).thenReturn(false);

        Volunteer volunteer1 = new Volunteer();
        volunteer1.setId(1L);
        volunteer1.setIsReady(1);
        volunteer1.setName("Bella");
        volunteer1.setShelterTableName("Bella");
        volunteer1.setTgUserId(1L);
        assertThrows(Exception.class, () -> volunteerService.updateVolunteer(volunteer1));
        verify(volunteerRepository).existsById((Long) any());
    }

    @Test
    void testDeleteVolunteer() {
        doNothing().when(volunteerRepository).delete((Volunteer) any());

        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        volunteerService.deleteVolunteer(volunteer);
        verify(volunteerRepository).delete((Volunteer) any());
        assertEquals(1L, volunteer.getId());
        assertEquals(1L, volunteer.getTgUserId());
        assertEquals("Bella", volunteer.getShelterTableName());
        assertEquals("Bella", volunteer.getName());
        assertEquals(1, volunteer.getIsReady());
    }
}

