package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Dog;
import edu.group5.petshelterbot.service.DogService;
import edu.group5.petshelterbot.service.OwnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллёр полей собак в базе данных собачьего приюта.
 */

@RestController
@RequestMapping("/dogs")
@Tag(name = "Контролёр работы с базой данных собачьего приюта.")
public class DogController {
    private DogService dogService;
    private OwnerService ownerService;

    public DogController(DogService dogService,OwnerService ownerService) {
        this.dogService = dogService;
        this.ownerService = ownerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление новых собак в базу данных")
    public ResponseEntity addNewDog(@RequestBody Dog dog) {
        return ResponseEntity.ok("Новый пёс добавлен в БД: " + dogService.saveDog(dog));
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение собаки по её ID")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<Dog> getDogByDataBaseID(@RequestParam long id) {
        Dog dog = dogService.getDogByID(id);
        if (dog == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dog);
    }

    @GetMapping("/all_dogs")
    @Operation(description = "Получение полного списка собак из приюта.")
    public ResponseEntity<List<Dog>> getAllDogs() {
        List<Dog> dogs = dogService.getAllDogs();
        if (dogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(dogs);
    }

    @PutMapping("/{id}")
    @Operation(description = "Редактирование параметров собаки по ID")
    public Dog putDog(@RequestBody Dog dog, @PathVariable long id)  throws Exception{
        Dog updatedDog = dogService.updateDog(dog);
        return dog;
    }



    @PutMapping("/{owner_id}/{dog_id}")
    @Operation(description = "Установка владельца собаки, где первое условие - id нового владельца из таблицы owners, а второе - id собаки" +
            ".")
    public ResponseEntity<String> putDogOwner(@RequestParam long owner_id, long dog_id) {
        dogService.setDogOwner(owner_id, dog_id);
        return ResponseEntity.ok("Собака " + dogService.getDogByID(dog_id).getName() + " под id " + dog_id + "" +
                " теперь с владельцем " + ownerService.getOwnerByID(owner_id).getName() + " под id " + owner_id);
    }


    @DeleteMapping("/deleteOwner/{id}")
    @Operation(description = "Установка владельца собаки как null, где условие - id собаки.")
    public ResponseEntity<String> deleteCatOwner(@RequestParam long id) {
        dogService.deleteOwnerId(id);
        return ResponseEntity.ok("Собака " + dogService.getDogByID(id).getName() + " под id " + id +
                " теперь не имеет владельца.");
    }




    @DeleteMapping("/{id}")
    @Operation(description = "Удаление собаки из списка по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteDog(@PathVariable int id) {
        dogService.deleteDog(dogService.getDogByID(id));
        return ResponseEntity.ok("Собака под id " + id + " удалён из списка.");
    }

}
