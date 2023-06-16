package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.service.CatService;
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

    @Autowired
    public CatController(CatService сatService) {
        this.catService = сatService;
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

    @PutMapping("/edit/{id}")
    @Operation(description = "Редактирование параметров кошки по ID")
    public Cat putCat(@RequestBody Cat cat, @PathVariable long id) {
        Cat updatedCat = catService.updateCat(cat);
        return cat;
    }

    @DeleteMapping("/delete/{id}")
    @Operation(description = "Удаление кошки из списка по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteCat(@PathVariable int id) {
        catService.deleteCat(catService.getCatByID(id));
        return ResponseEntity.ok("Кошка под id " + id + " удалён из списка.");
    }

}
