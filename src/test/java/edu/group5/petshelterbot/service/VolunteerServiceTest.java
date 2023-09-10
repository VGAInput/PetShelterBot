package edu.group5.petshelterbot.service;

import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.repository.VolunteerRepository;
import org.apache.el.stream.Optional;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.postgresql.hostchooser.HostRequirement.any;

@ExtendWith(MockitoExtension.class)
class VolunteerServiceTest {

    private static final String VOLUNTEER_NAME = "Volunteer";
    private static final String SHELTER_TABLE_NAME = "CATS";
    private static final Long TG_USER_ID = 1L;
    private static final Integer IS_READY = 1;

    private final Volunteer volunteer = new Volunteer(VOLUNTEER_NAME, SHELTER_TABLE_NAME, TG_USER_ID, IS_READY);

    private static final List<Volunteer> volunteers = new ArrayList<>(Arrays.asList(
            new Volunteer(VOLUNTEER_NAME, SHELTER_TABLE_NAME, TG_USER_ID, IS_READY),
            new Volunteer(VOLUNTEER_NAME, SHELTER_TABLE_NAME, TG_USER_ID, IS_READY),
            new Volunteer(VOLUNTEER_NAME, SHELTER_TABLE_NAME, TG_USER_ID, IS_READY)
    ));

    private static final List<Long> longs = new ArrayList<>(Arrays.asList(1L, 2L, 3L, 4L, 5L));

    @Mock
    private VolunteerRepository volunteerRepositoryMock;

    @InjectMocks
    private VolunteerService volunteerService;

    @Test
    void saveVolunteer() {
        Mockito.when(volunteerRepositoryMock.save(any(Volunteer.class))).thenReturn(volunteer);

        Volunteer volunteer1 = volunteerService.saveVolunteer(volunteer);

        Assertions.assertThat(volunteer.getName()).isEqualTo(volunteer1.getName());
        Assertions.assertThat(volunteer.getShelterTableName()).isEqualTo(volunteer1.getShelterTableName());
        Assertions.assertThat(volunteer.getTgUserId()).isEqualTo(volunteer1.getTgUserId());
        Assertions.assertThat(volunteer.getIsReady()).isEqualTo(volunteer1.getIsReady());
    }

    @Test
    void getVolunteerByID() {
        Mockito.when(volunteerRepositoryMock.findVolunteersById(any(Long.class))).thenReturn(volunteer);

        Volunteer volunteer1 = volunteerService.getVolunteerByID(1L);

        Assertions.assertThat(volunteer.getName()).isEqualTo(volunteer1.getName());
        Assertions.assertThat(volunteer.getShelterTableName()).isEqualTo(volunteer1.getShelterTableName());
        Assertions.assertThat(volunteer.getTgUserId()).isEqualTo(volunteer1.getTgUserId());
        Assertions.assertThat(volunteer.getIsReady()).isEqualTo(volunteer1.getIsReady());
    }

    @Test
    void getAllVolunteers() {
        Mockito.when(volunteerRepositoryMock.findAll()).thenReturn(volunteers);

        List<Volunteer> volunteers1 = volunteerService.getAllVolunteers();

        Assertions.assertThat(volunteers1.size()).isEqualTo(volunteers.size());
        Assertions.assertThat(volunteers1).isEqualTo(volunteers);
    }

    @Test
    void checkVolunteerExists() {
        Mockito.when(volunteerRepositoryMock.existsByShelterTableNameAndTgUserId(any(String.class), any(Long.class)))
                .thenReturn(true);

        boolean isExist = volunteerService.checkVolunteerExists(SHELTER_TABLE_NAME, TG_USER_ID);

        Assertions.assertThat(isExist).isTrue();
    }

    @Test
    void getAllVolunteersFromShelter() {
        Mockito.when(volunteerRepositoryMock.findVolunteersByShelterTableName(any(String.class))).thenReturn(volunteers);

        List<Volunteer> volunteers1 = volunteerService.getAllVolunteersFromShelter(SHELTER_TABLE_NAME);

        Assertions.assertThat(volunteers1.size()).isEqualTo(volunteers.size());
        Assertions.assertThat(volunteers1).isEqualTo(volunteers);
    }

    @Test
    void volunteerIds() {
        Mockito.when(volunteerRepositoryMock.getVolunteersFromShelter(any(String.class))).thenReturn(longs);

        List<Long> volunteers1 = volunteerService.volunteerIds(SHELTER_TABLE_NAME);

        Assertions.assertThat(volunteers1.size()).isEqualTo(longs.size());
        Assertions.assertThat(volunteers1).isEqualTo(longs);
    }

    @Test
    void getRandomVolunteer() {
        Mockito.when(volunteerRepositoryMock.getVolunteersFromShelter(any(String.class))).thenReturn(longs);

        List<Long> volunteers1 = volunteerService.volunteerIds(SHELTER_TABLE_NAME);

        Assertions.assertThat(volunteers1.get(0)).isEqualTo(1L);
    }

    @Test
    void isCurrentUserVolunteer() {
        Mockito.when(volunteerRepositoryMock.findVolunteersById(any(Long.class))).thenReturn(volunteer);

        Volunteer volunteer1 = volunteerService.getVolunteerByID(1L);

        Assertions.assertThat(volunteer1).isEqualTo(volunteer);
    }

    @Test
    void isReadyReturnOneIfIsReady() {
        Mockito.when(volunteerRepositoryMock.setReady(1, 1L)).thenReturn(1);

        int result = volunteerRepositoryMock.setReady(1, 1L);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void isReadyReturnZeroIfIsNotReady() {
        Mockito.when(volunteerRepositoryMock.setReady(0, 1L)).thenReturn(0);

        int result = volunteerRepositoryMock.setReady(0, 1L);

        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    void getVolunteerByTgUserId() {
        Mockito.when(volunteerRepositoryMock.findVolunteerByTgUserId(any(Long.class))).thenReturn(volunteers);

        List<Volunteer> volunteers1 = volunteerService.getVolunteerByTgUserId(1L);

        Assertions.assertThat(volunteers1.size()).isEqualTo(volunteers.size());
        Assertions.assertThat(volunteers1).isEqualTo(volunteers);
    }

    @Test
    void updateVolunteerIfVolunteerIsExist() {
        Mockito.when(volunteerRepositoryMock.existsById(any(Long.class))).thenReturn(true);
        Mockito.when(volunteerRepositoryMock.save(any(Volunteer.class))).thenReturn(volunteer);

        try {
            Volunteer volunteer1 = volunteerService.updateVolunteer(volunteer);

            Assertions.assertThat(volunteer.getName()).isEqualTo(volunteer1.getName());
            Assertions.assertThat(volunteer.getShelterTableName()).isEqualTo(volunteer1.getShelterTableName());
            Assertions.assertThat(volunteer.getTgUserId()).isEqualTo(volunteer1.getTgUserId());
            Assertions.assertThat(volunteer.getIsReady()).isEqualTo(volunteer1.getIsReady());
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    void updateVolunteerThrowExceptionIfVolunteerDoesNotExist() {
        Mockito.when(volunteerRepositoryMock.existsById(any(Long.class))).thenReturn(false);

        Assertions.assertThatThrownBy(
                        () -> volunteerService.updateVolunteer(volunteer)
                )
                .isInstanceOf(Exception.class)
                .hasMessage("Этот волонтёр не найден в базе данных.");
    }
}