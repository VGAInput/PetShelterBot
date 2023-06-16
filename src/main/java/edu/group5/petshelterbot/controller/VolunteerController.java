package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.service.VolunteerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * Контроллёр полей волонтёров.
 */

@RestController
@RequestMapping("/volunteers")
@Tag(name = "Контролёр работы с базой данных волонтёров.")
public class VolunteerController {
    private VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление новых волонтёров в базу данных." +
            "Необходимо учитывать корректный telegram id (tgUserId) волонтёра.")
    public ResponseEntity addNewVolunteer(@RequestBody Volunteer volunteer) {
        return ResponseEntity.ok("Новый волонтёр добавлен в БД: " + volunteerService.saveVolunteer(volunteer));
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение волонтёра путём ввода его id в базы данных (не tgId)")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<Volunteer> getVolunterByDataBaseID(@RequestParam long id) {
        Volunteer volunteer = volunteerService.getVolunteerByID(id);
        if (volunteer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(volunteer);
    }

    @GetMapping("/all_shelters")
    @Operation(description = "Получение полного списка волонтёров из всех приютов.")
    public ResponseEntity<List<Volunteer>> getAllVolunteers() {
        List<Volunteer> volunteers = volunteerService.getAllVolunteers();
        if (volunteers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(volunteers);
    }

    @PutMapping("/edit/{id}")
    @Operation(description = "Редактирование параметров волонтёра по ID")
    public Volunteer putVolunteer(@RequestBody Volunteer volunteer, @PathVariable long id) {
        Volunteer updatedVolunteer = volunteerService.updateVolunteer(volunteer);
        return volunteer;
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Удаление волонтёра по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteVolunteer(@PathVariable int id) {
        volunteerService.deleteVolunteer(volunteerService.getVolunteerByID(id));
        return ResponseEntity.ok("Волонтёр под id " + id + " удалён из списка.");
    }

}
