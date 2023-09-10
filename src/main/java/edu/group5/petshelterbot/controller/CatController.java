package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.service.CatService;
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
 * Контроллёр полей котов в базе данных кошачьего приюта.
 */

@RestController
@RequestMapping("/cats")
@Tag(name = "Контролёр работы с базой данных кошачьего приюта.")
public class CatController {
    private CatService catService;
    private OwnerService ownerService;


    public CatController(CatService сatService, OwnerService ownerService) {
        this.catService = сatService;
        this.ownerService = ownerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Добавление новых котов в базу данных")
    public ResponseEntity addNewCat(@RequestBody Cat сat) {
        return ResponseEntity.ok("Новый кот добавлен в БД: " + catService.saveCat(сat));
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение кота по её ID")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<Cat> getCatByDataBaseID(@RequestParam long id) {
        Cat cat = catService.getCatByID(id);
        if (cat == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cat);
    }

    @GetMapping("/all_cats")
    @Operation(description = "Получение полного списка котов из приюта.")
    public ResponseEntity<List<Cat>> getAllCats() {
        List<Cat> cats = catService.getAllCats();
        if (cats.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(cats);
    }

    @PutMapping("/{id}")
    @Operation(description = "Редактирование параметров кошки по ID")
    public Cat putCat(@RequestBody Cat cat, @PathVariable long id) throws Exception {
        Cat updatedCat = catService.updateCat(cat);
        return cat;
    }

    @PutMapping("/{owner_id}/{cat_id}")
    @Operation(description = "Установка владельца кота, где первое условие - id нового владельца из таблицы owners, а второе - id " +
            "кота.")
    public ResponseEntity<String> putCatOwner(@RequestParam long owner_id, long cat_id) {
        catService.setCatOwner(owner_id, cat_id);
        return ResponseEntity.ok("Кошка " + catService.getCatByID(cat_id).getName() + " под id " + cat_id + "" +
                " теперь с владельцем " + ownerService.getOwnerByID(owner_id).getName() + " под id " + owner_id);
    }


    @DeleteMapping("/deleteOwner/{id}")
    @Operation(description = "Установка владельца кота как null, где условие - id кота.")
    public ResponseEntity<String> deleteCatOwner(@RequestParam long id) {
        catService.deleteOwnerId(id);
        return ResponseEntity.ok("Кошка " + catService.getCatByID(id).getName() + " под id " + id +
                " теперь не имеет владельца.");
    }


    @DeleteMapping("/{id}")
    @Operation(description = "Удаление кошки из списка по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteCat(@PathVariable int id) {
        catService.deleteCat(catService.getCatByID(id));
        return ResponseEntity.ok("Кошка под id " + id + " удалён из списка.");
    }

}
