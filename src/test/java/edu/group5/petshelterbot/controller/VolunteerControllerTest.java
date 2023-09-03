package edu.group5.petshelterbot.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.service.VolunteerService;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {VolunteerController.class})
@ExtendWith(SpringExtension.class)
class VolunteerControllerTest {
    @Autowired
    private VolunteerController volunteerController;

    @MockBean
    private VolunteerService volunteerService;

    @Test
    void testGetAllVolunteers() throws Exception {
        when(volunteerService.getAllVolunteers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/volunteers/all_shelters");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(volunteerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testGetAllVolunteers2() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);

        ArrayList<Volunteer> volunteerList = new ArrayList<>();
        volunteerList.add(volunteer);
        when(volunteerService.getAllVolunteers()).thenReturn(volunteerList);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/volunteers/all_shelters");
        MockMvcBuilders.standaloneSetup(volunteerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("[{\"id\":1,\"name\":\"Bella\",\"tgUserId\":1,\"shelterTableName\":\"Bella\",\"isReady\":1}]"));
    }

    @Test
    void testAddNewVolunteer() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        String content = (new ObjectMapper()).writeValueAsString(volunteer);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/volunteers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(volunteerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    @Test
    void testGetVolunterByDataBaseID() throws Exception {
        Volunteer volunteer = new Volunteer();
        volunteer.setId(1L);
        volunteer.setIsReady(1);
        volunteer.setName("Bella");
        volunteer.setShelterTableName("Bella");
        volunteer.setTgUserId(1L);
        when(volunteerService.getVolunteerByID(anyLong())).thenReturn(volunteer);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/volunteers/42");
        MockHttpServletRequestBuilder requestBuilder = getResult.param("id", String.valueOf(1L));
        MockMvcBuilders.standaloneSetup(volunteerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"id\":1,\"name\":\"Bella\",\"tgUserId\":1,\"shelterTableName\":\"Bella\",\"isReady\":1}"));
    }
}

