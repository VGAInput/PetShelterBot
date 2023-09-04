package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Owner;
import edu.group5.petshelterbot.entity.Volunteer;
import edu.group5.petshelterbot.service.OwnerService;
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

/**
 * Контроллёр полей возможных владельцев.
 */

@RestController
@RequestMapping("/owners")
@Tag(name = "Контролёр работы с базой данных (потенциальных) владельцев.")
public class OwnerController {
    private OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление новых пользователей в базу данных." +
            "Необходимо учитывать корректный telegram id (tgUserId) пользователя.")
    public ResponseEntity addNewOwner(@RequestBody Owner owner) throws Exception {
        return ResponseEntity.ok("Новый пользователь добавлен в БД: " + ownerService.saveOwner(owner));
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение пользователя путём ввода его id в базы данных (не tgId)")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<Owner> getOwnerByDataBaseID(@RequestParam long id) {
        Owner owner = ownerService.getOwnerByID(id);
        if (owner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(owner);
    }

    @GetMapping("/get_cats/{id}")
    @Operation(description = "Поиск ктоов владельца в БД. - где вводится id владельца, и получается список с ID котов.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<List<Integer>> getCats(@RequestParam long id) {
        List<Integer> cats = ownerService.getCatsIDs(id);
        if (cats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cats);
    }

    @GetMapping("/get_dogs/{id}")
    @Operation(description = "Поиск собак владельца в БД. - где вводится id владельца, и получается список ID собак.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<List<Integer>> getDogs(@RequestParam long id) {
        List<Integer> dogs = ownerService.getDogsIDs(id);
        if (dogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dogs);
    }

    @GetMapping("/all_owners")
    @Operation(description = "Получение полного списка пользователей.")
    public ResponseEntity<List<Owner>> getAllOwners() {
        List<Owner> Owners = ownerService.getAllOwners();
        if (Owners.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Owners);
    }

    @PutMapping("/{id}")
    @Operation(description = "Редактирование параметров пользователя")
    public Owner putOwner(@RequestBody Owner owner, @PathVariable long id) throws Exception {
        Owner updatedOwner = ownerService.updateOwner(owner);
        return owner;
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Удаление пользователя по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteOwner(@PathVariable int id) {
        ownerService.deleteOwner(ownerService.getOwnerByID(id));
        return ResponseEntity.ok("Пользовател под id " + id + " удалён из списка.");
    }

}
