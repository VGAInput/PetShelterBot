package edu.group5.petshelterbot.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import edu.group5.petshelterbot.entity.Report;
import edu.group5.petshelterbot.repository.ReportRepository;
import edu.group5.petshelterbot.service.ReportService;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ReportController.class})
@ExtendWith(SpringExtension.class)
class ReportControllerTest {
    @Autowired
    private ReportController reportController;

    @MockBean
    private ReportService reportService;

    @Test
    void testGetReportById() {
        Report report = new Report();
        report.setId(1L);
        report.setIsApproved(1);
        report.setMessageId(1L);
        report.setOwnerId(1L);
        report.setReportDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        report.setReportText("Report Text");
        report.setVolunteerId(1L);
        ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.findReportById(anyLong())).thenReturn(report);
        ResponseEntity<Report> actualReportById = (new ReportController(new ReportService(reportRepository)))
                .getReportById(1L);
        assertTrue(actualReportById.hasBody());
        assertTrue(actualReportById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualReportById.getStatusCode());
        verify(reportRepository).findReportById(anyLong());
    }

    @Test
    @Disabled()
    void testGetReportById2() {
        (new ReportController(null)).getReportById(1L);
    }

    @Test
    void testGetReportById3() {
        Report report = new Report();
        report.setId(1L);
        report.setIsApproved(1);
        report.setMessageId(1L);
        report.setOwnerId(1L);
        report.setReportDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        report.setReportText("Report Text");
        report.setVolunteerId(1L);
        ReportService reportService = mock(ReportService.class);
        when(reportService.getReportByID(anyLong())).thenReturn(report);
        ResponseEntity<Report> actualReportById = (new ReportController(reportService)).getReportById(1L);
        assertTrue(actualReportById.hasBody());
        assertTrue(actualReportById.getHeaders().isEmpty());
        assertEquals(HttpStatus.OK, actualReportById.getStatusCode());
        verify(reportService).getReportByID(anyLong());
    }

    @Test
    void testGetReportByOwnerId() {
               ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.findReportByOwnerId(anyLong())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Report>> actualReportByOwnerId = (new ReportController(new ReportService(reportRepository)))
                .getReportByOwnerId(1L);
        assertNull(actualReportByOwnerId.getBody());
        assertEquals(HttpStatus.NO_CONTENT, actualReportByOwnerId.getStatusCode());
        assertTrue(actualReportByOwnerId.getHeaders().isEmpty());
        verify(reportRepository).findReportByOwnerId(anyLong());
    }

    @Test
    void testGetReportByOwnerId2() {
        Report report = new Report();
        report.setId(1L);
        report.setIsApproved(1);
        report.setMessageId(1L);
        report.setOwnerId(1L);
        report.setReportDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        report.setReportText("Report Text");
        report.setVolunteerId(1L);

        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(report);
        ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.findReportByOwnerId(anyLong())).thenReturn(reportList);
        ResponseEntity<List<Report>> actualReportByOwnerId = (new ReportController(new ReportService(reportRepository)))
                .getReportByOwnerId(1L);
        assertTrue(actualReportByOwnerId.hasBody());
        assertEquals(HttpStatus.OK, actualReportByOwnerId.getStatusCode());
        assertTrue(actualReportByOwnerId.getHeaders().isEmpty());
        verify(reportRepository).findReportByOwnerId(anyLong());
    }

    @Test
    @Disabled()
    void testGetReportByOwnerId3() {
        (new ReportController(null)).getReportByOwnerId(1L);
    }

    @Test
    void testGetReportByOwnerId4() {
              ReportService reportService = mock(ReportService.class);
        when(reportService.getReportByOwnerID(anyLong())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Report>> actualReportByOwnerId = (new ReportController(reportService)).getReportByOwnerId(1L);
        assertNull(actualReportByOwnerId.getBody());
        assertEquals(HttpStatus.NO_CONTENT, actualReportByOwnerId.getStatusCode());
        assertTrue(actualReportByOwnerId.getHeaders().isEmpty());
        verify(reportService).getReportByOwnerID(anyLong());
    }

    @Test
    void testGetReportByVolunteerId() {
               ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.findReportByVolunteerId(anyLong())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Report>> actualReportByVolunteerId = (new ReportController(
                new ReportService(reportRepository))).getReportByVolunteerId(1L);
        assertNull(actualReportByVolunteerId.getBody());
        assertEquals(HttpStatus.NO_CONTENT, actualReportByVolunteerId.getStatusCode());
        assertTrue(actualReportByVolunteerId.getHeaders().isEmpty());
        verify(reportRepository).findReportByVolunteerId(anyLong());
    }

    @Test
    void testGetReportByVolunteerId2() {
        Report report = new Report();
        report.setId(1L);
        report.setIsApproved(1);
        report.setMessageId(1L);
        report.setOwnerId(1L);
        report.setReportDate(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()));
        report.setReportText("Report Text");
        report.setVolunteerId(1L);

        ArrayList<Report> reportList = new ArrayList<>();
        reportList.add(report);
        ReportRepository reportRepository = mock(ReportRepository.class);
        when(reportRepository.findReportByVolunteerId(anyLong())).thenReturn(reportList);
        ResponseEntity<List<Report>> actualReportByVolunteerId = (new ReportController(
                new ReportService(reportRepository))).getReportByVolunteerId(1L);
        assertTrue(actualReportByVolunteerId.hasBody());
        assertEquals(HttpStatus.OK, actualReportByVolunteerId.getStatusCode());
        assertTrue(actualReportByVolunteerId.getHeaders().isEmpty());
        verify(reportRepository).findReportByVolunteerId(anyLong());
    }

    @Test
    @Disabled()
    void testGetReportByVolunteerId3() {
               (new ReportController(null)).getReportByVolunteerId(1L);
    }

    @Test
    void testGetReportByVolunteerId4() {
        ReportService reportService = mock(ReportService.class);
        when(reportService.getReportByVolunteerID(anyLong())).thenReturn(new ArrayList<>());
        ResponseEntity<List<Report>> actualReportByVolunteerId = (new ReportController(reportService))
                .getReportByVolunteerId(1L);
        assertNull(actualReportByVolunteerId.getBody());
        assertEquals(HttpStatus.NO_CONTENT, actualReportByVolunteerId.getStatusCode());
        assertTrue(actualReportByVolunteerId.getHeaders().isEmpty());
        verify(reportService).getReportByVolunteerID(anyLong());
    }
}

