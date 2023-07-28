package edu.group5.petshelterbot.controller;


import edu.group5.petshelterbot.entity.Cat;
import edu.group5.petshelterbot.entity.Report;
import edu.group5.petshelterbot.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reports")
@Tag(name = "Контролёр работы с базой отчётов от владельцев.")
public class ReportController {

    private ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/{id}")
    @Operation(description = "Получение отчёта по ID")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<Report> getReportById(@RequestParam long id) {
        Report report = reportService.getReportByID(id);
        if (report == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(report);
    }

    @GetMapping("/{owner_id}")
    @Operation(description = "Получение отчётов от владельца по ID")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<List<Report>> getReportByOwnerId(@RequestParam long owner_id) {
        List<Report> reports = reportService.getReportByOwnerID(owner_id);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/{volunteer_id}")
    @Operation(description = "Получение отчётов по ID волонтёра.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<List<Report>> getReportByVolunteerId(@RequestParam long volunteer_id) {
        List<Report> reports = reportService.getReportByVolunteerID(volunteer_id);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(reports);
    }

    @DeleteMapping("/{delete_id}")
    @Operation(description = "Удаление кошки из списка по ID.")
    @Parameters(value = {
            @Parameter(name = "id", example = "1")
    })
    public ResponseEntity<String> deleteCat(@PathVariable int delete_id) {
        reportService.deleteReport(reportService.getReportByID(delete_id));
        return ResponseEntity.ok("Отчёт под ID " + delete_id + " удалён из списка.");
    }


}
